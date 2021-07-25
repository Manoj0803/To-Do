package com.example.todo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.data.models.ToDoData
import com.example.todo.databinding.FragmentUpdateBinding
import com.example.todo.viewModel.SharedViewModel
import com.example.todo.viewModel.ToDoViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding : FragmentUpdateBinding
    private val sharedViewModel : SharedViewModel by viewModels()
    private val viewModel : ToDoViewModel by viewModels()
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update,container,false)
        binding.currentPrioritySpinner.onItemSelectedListener = sharedViewModel.listener

        binding.toDoData = args.currentItem

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save_menu -> updateitem()
            R.id.delete_menu -> {
                deleteItem()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.delete(args.currentItem)
            Toast.makeText(
                context,
                "Successfully Deleted : ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            )
                .show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("No") { _, _ -> }

        builder.setTitle("Delete '${args.currentItem.title}' ?")
        builder.setMessage("Are you sure you want to delete '${args.currentItem.title}' ?")
        builder.create().show()
    }

    private fun updateitem() {

        val title = binding.currentTitleEditText.text.toString()
        val priority = binding.currentPrioritySpinner.selectedItem.toString()
        val description = binding.currentDescription.text.toString()

        val isValid = sharedViewModel.verifyData(title,description)

        if(isValid) {
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                sharedViewModel.getPriority(priority),
                description
            )

            Log.i("UpdateFragment","$updatedItem")
            viewModel.update(updatedItem)
            Toast.makeText(context, "Successfully Updated.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(context, "Please fill out all fields..", Toast.LENGTH_LONG).show()
        }

    }
}