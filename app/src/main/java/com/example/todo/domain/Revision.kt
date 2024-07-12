package com.example.todo.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "revision")
data class Revision(
    @PrimaryKey
    val revision: Int,
    val unSyncWork: Boolean
)
