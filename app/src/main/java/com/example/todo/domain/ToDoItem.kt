package com.example.todo.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Entity(tableName = "todo_items")
data class ToDoItem(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: String,
    @Expose
    @SerializedName("importance")
    var importance: String,
    @Expose
    @SerializedName("done")
    var completed: Boolean,
    @Expose
    @SerializedName("text")
    var description: String,
    @Expose(serialize = false,deserialize = false)
    var deadline: Long?,
    @Expose
    @SerializedName("created_at")
    val createdAt: Long = 0,
    @Expose
    @SerializedName("changed_at")
    var changedAt: Long? = 0,
    @Expose
    @SerializedName("last_updated_by")
    var lastUpdatedBy: String = "MIIV"
)