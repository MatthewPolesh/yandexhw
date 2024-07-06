package com.example.todo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListWrapper(
    @Expose
    @SerializedName("list")
    val list: List<ToDoItem>
)