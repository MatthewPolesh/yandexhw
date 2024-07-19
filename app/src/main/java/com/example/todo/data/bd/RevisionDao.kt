package com.example.todo.data.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.domain.Revision

@Dao
interface RevisionDao {
    @Query("SELECT * FROM revision LIMIT 1")
    suspend fun getRevision(): Revision?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRevision(revision: Revision)

    @Query("DELETE FROM revision")
    suspend fun deleteAll()
}