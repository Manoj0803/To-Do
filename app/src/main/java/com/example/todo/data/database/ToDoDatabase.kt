package com.example.todo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.data.models.ToDoData
import com.example.todo.data.converter.Converter
import com.example.todo.data.dao.ToDoDao

@Database(
    entities = [ToDoData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao() : ToDoDao

    companion object{

        @Volatile
        private var INSTANCE : ToDoDatabase?= null

        fun getDatabase(context : Context): ToDoDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "ToDo_Database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}