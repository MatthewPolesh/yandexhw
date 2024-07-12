package com.example.todo.domain

import com.example.todo.R

enum class Importance(val description: Int) {
    Low(R.string.imp_low),
    Medium(R.string.imp_medium),
    High(R.string.imp_high)
}