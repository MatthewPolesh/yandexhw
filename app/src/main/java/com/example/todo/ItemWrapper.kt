package com.example.todo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemWrapper(
    @Expose
    @SerializedName("element")
    val element: ToDoItem
)
