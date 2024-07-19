package com.example.todo.data.network

import com.example.todo.domain.ToDoItem

data class ApiResponse(
    val status: String,
    val list: List<ToDoItem>,
    val revision: Int
)
