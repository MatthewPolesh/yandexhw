package com.example.todo

import android.app.Application
import com.example.todo.di.components.AppComponent
import com.example.todo.di.components.DaggerAppComponent

class MyApp: Application() {
    val appComponent: AppComponent by lazy { DaggerAppComponent.factory().create(applicationContext) }
}