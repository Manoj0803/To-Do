package com.example.todo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todo.data.dao.ToDoDao
import com.example.todo.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData : LiveData<List<ToDoData>> = toDoDao.getAllData()

    val sortByHighPriority : LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()

    val sortByLowPriority : LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()

    suspend fun insert(toDoData: ToDoData){
        toDoDao.insert(toDoData)
    }

    suspend fun update(toDoData: ToDoData){
        Log.i("UpdateFragment","Final Update $toDoData")
        toDoDao.update(toDoData)
    }

    suspend fun delete(toDoData: ToDoData){
        toDoDao.delete(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAllData()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchDatabase(searchQuery)
    }

}