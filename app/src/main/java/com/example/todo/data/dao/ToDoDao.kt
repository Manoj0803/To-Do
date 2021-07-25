package com.example.todo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("select * from todo_table order by id")
    fun getAllData() : LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDoData : ToDoData)

    @Update
    suspend fun update(toDoData: ToDoData)

    @Delete
    suspend fun delete(toDoData: ToDoData)

    @Query("Delete from todo_table")
    suspend fun deleteAllData()

    @Query("select * from todo_table where title Like :searchQuery")
    fun searchDatabase(searchQuery : String) : LiveData<List<ToDoData>>

    @Query("select * from todo_table order by case when priority like 'H%' then 1 when priority like 'M%' then 2 when priority like 'L%' then 3 end")
    fun sortByHighPriority() : LiveData<List<ToDoData>>

    @Query("select * from todo_table order by case when priority like 'L%' then 1 when priority like 'M%' then 2 when priority like 'H%' then 3 end")
    fun sortByLowPriority() : LiveData<List<ToDoData>>

}