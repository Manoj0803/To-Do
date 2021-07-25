package com.example.todo.bindingAdapter

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoData
import com.example.todo.fragments.list.ListFragmentDirections
import com.example.todo.fragments.update.UpdateFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {
    companion object{
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view : FloatingActionButton,navigate : Boolean) {
            view.setOnClickListener {
                if(navigate){
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("emptyDataBase")
        @JvmStatic
        fun emptyDatabase(view : View,emptyDatabase : MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view : Spinner,priority: Priority){
            when(priority){
                Priority.HIGH -> view.setSelection(0)
                Priority.MEDIUM -> view.setSelection(1)
                Priority.LOW -> view.setSelection(2)
            }
        }

        @BindingAdapter("parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(view : CardView,priority: Priority){
            when(priority){
                Priority.HIGH -> {view.setCardBackgroundColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.red
                        )
                    )}

                Priority.MEDIUM -> {
                    view.setCardBackgroundColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.yellow
                            )
                        )
                }

                Priority.LOW -> {
                    view.setCardBackgroundColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                }
            }
        }

        @BindingAdapter("navigateToUpdateFragment")
        @JvmStatic
        fun navigate(view : View,toDoData : ToDoData){
            view.setOnClickListener {
                    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoData)
                    view.findNavController().navigate(action)
            }
        }

    }
}

