package com.example.todo.Network

import com.example.todo.ToDoItem

data class ApiResponse(
    val status: String,
    val list: List<ToDoItem>,
    val revision: Int
)
