package com.example.todo.viewModel

import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyData = MutableLiveData<Boolean>(false)

    fun checkData(toDoData: List<ToDoData>){
        emptyData.value = toDoData.isEmpty()
    }

    val listener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when(position){
                0 -> {(parent?.getChildAt(0) as TextView)
                    .setTextColor(ContextCompat.getColor(application, R.color.red))}

                1 -> {(parent?.getChildAt(0) as TextView)
                    .setTextColor(ContextCompat.getColor(application, R.color.yellow))}

                2 -> {(parent?.getChildAt(0) as TextView)
                    .setTextColor((ContextCompat.getColor(application, R.color.green)))}
            }
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    fun getPriority(priority: String): Priority = when(priority){
        "High Priority" -> Priority.HIGH
        "Medium Priority" -> Priority.MEDIUM
        else -> Priority.LOW
    }

    fun verifyData(title: String, description: String): Boolean {
        return !(title.isEmpty() or description.isEmpty())
    }
}