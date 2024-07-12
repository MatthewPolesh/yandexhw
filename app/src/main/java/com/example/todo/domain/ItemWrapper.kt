package com.example.todo.domain

import com.example.todo.domain.ToDoItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemWrapper(
    @Expose
    @SerializedName("element")
    val element: ToDoItem
)
