package com.example.todo

import java.util.Date

data class ToDoItem(
    var id: String,
    var importance: Int,
    var completed: Boolean,
    var description: String,
    var deadline: String,
    val dateCreated: String,
    var dateChanged: String
)