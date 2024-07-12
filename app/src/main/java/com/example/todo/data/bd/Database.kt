package com.example.todo.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.domain.Revision
import com.example.todo.domain.ToDoItem

@Database(entities = [ToDoItem::class, Revision::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun toDoItemDao(): ToDoItemDao
    abstract fun revisionDao(): RevisionDao

    companion object{
        @Volatile
        private var _instance:AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return _instance?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo_database"
                ).build()
                _instance = instance
                instance
            }
        }
    }
}