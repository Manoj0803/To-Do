package com.example.todo.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.example.todo.R
import com.example.todo.data.models.ToDoData
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.fragments.list.adapter.ListAdapter
import com.example.todo.fragments.list.adapter.SwipeToDelete
import com.example.todo.utils.hideKeyboard
import com.example.todo.viewModel.SharedViewModel
import com.example.todo.viewModel.ToDoViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment() , SearchView.OnQueryTextListener {

    private lateinit var binding : FragmentListBinding
    private val viewModel : ToDoViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by viewModels()

    private val listAdapter : ListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        setUpRecyclerView()
        hideKeyboard(requireActivity())

        viewModel.getAllData.observe(viewLifecycleOwner, { toDoDataList ->
            sharedViewModel.checkData(toDoDataList)
            listAdapter.differ.submitList(toDoDataList)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.apply {
            recyclerView.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            recyclerView.adapter = listAdapter
            recyclerView.itemAnimator=SlideInUpAnimator().apply{
                addDuration=300
            }
            swipeToDelete(recyclerView)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallBack = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = listAdapter.differ.currentList[viewHolder.adapterPosition]
                viewModel.delete(itemToDelete)
                restoreDeleteData(viewHolder.itemView,itemToDelete)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteData(view : View,deleteItem : ToDoData){
        val snackBar = Snackbar.make(view,"Deleted : ${deleteItem.title}",Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo") {
            viewModel.insert(deleteItem)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
        val search = menu.findItem(R.id.search_menu)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete_all_menu -> deleteAll()

            R.id.priority_high_menu -> viewModel.sortByHighPriority.observe(
                viewLifecycleOwner,
                { list ->
                    list?.let {
                        listAdapter.differ.submitList(list)
                    }
                }
            )

            R.id.priority_low_menu -> viewModel.sortByLowPriority.observe(
                viewLifecycleOwner,
                { list->
                    list?.let{
                        listAdapter.differ.submitList(list)
                    }
                }
            )

        }


        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            viewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully deleted all data.",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete All Data.?")
        builder.setMessage("Are you sure you want to delete all the data?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner,{ list->
            list?.let {
                listAdapter.differ.submitList(list)
            }
        })
    }

    override fun onQueryTextChange(query: String?): Boolean {
        query?.let {
            searchDatabase(query)
        }
        return true
    }
}