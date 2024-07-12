package com.example.todo.data.bd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.todo.domain.ToDoItem

@Dao
interface ToDoItemDao {
    @Query("SELECT * FROM todo_items")
    suspend fun getItemList(): List<ToDoItem>
    @Insert
    suspend fun addItem(item: ToDoItem)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(list: List<ToDoItem>)
    @Transaction
    suspend fun updateList(list: List<ToDoItem>) {
        deleteAll()
        insertAll(list)
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ToDoItem>)

    @Query("DELETE FROM todo_items")
    suspend fun deleteAll()
    @Update
    suspend fun updateItem(item: ToDoItem)
    @Delete
    suspend fun deleteItem(item: ToDoItem)
}