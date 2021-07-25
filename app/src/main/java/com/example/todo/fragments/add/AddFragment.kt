package com.example.todo.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.data.models.ToDoData
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.viewModel.SharedViewModel
import com.example.todo.viewModel.ToDoViewModel

class AddFragment : Fragment() {

    private lateinit var binding : FragmentAddBinding
    private val sharedViewModel : SharedViewModel by viewModels()
    private val viewModel : ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add,container,false)
        binding.prioritySpinner.onItemSelectedListener = sharedViewModel.listener
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.add_menu){
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {

        val title = binding.titleEditText.text.toString()
        val priority = binding.prioritySpinner.selectedItem.toString()
        val description = binding.descriptionEditText.text.toString()

        val isValid = sharedViewModel.verifyData(title,description)

        if(isValid) {
            val toDoData = ToDoData(
                0,
                title,
                sharedViewModel.getPriority(priority),
                description
            )

            viewModel.insert(toDoData)
            Toast.makeText(context, "Successfully Added.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(context, "Please fill out all fields..", Toast.LENGTH_LONG).show()
        }
    }
}