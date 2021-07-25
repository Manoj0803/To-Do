package com.example.todo.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.database.ToDoDatabase
import com.example.todo.data.models.ToDoData
import com.example.todo.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private var repository : ToDoRepository

    val sortByHighPriority : LiveData<List<ToDoData>>
    val sortByLowPriority : LiveData<List<ToDoData>>


    val getAllData : LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData

        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insert(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(toDoData)
        }
    }

    fun update(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(toDoData)
        }
    }

    fun delete(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(toDoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery : String):LiveData<List<ToDoData>>{
        return repository.searchDatabase(searchQuery)
    }

}