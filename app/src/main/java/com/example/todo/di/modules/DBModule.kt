package com.example.todo.di.modules

import android.content.Context
import androidx.room.Room
import com.example.todo.data.bd.AppDatabase
import com.example.todo.data.bd.RevisionDao
import com.example.todo.data.bd.ToDoItemDao
import com.example.todo.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DBModule {
    @Provides
    @ApplicationScope
    fun provideDataBase(context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "todo_database").build()
    }

    @Provides
    @ApplicationScope
    fun provideTodoItemsDao(context: Context): ToDoItemDao{
        return AppDatabase.getDatabase(context).toDoItemDao()
    }
    @Provides
    @ApplicationScope
    fun provideRevisionDao(context: Context): RevisionDao{
        return AppDatabase.getDatabase(context).revisionDao()
    }

}