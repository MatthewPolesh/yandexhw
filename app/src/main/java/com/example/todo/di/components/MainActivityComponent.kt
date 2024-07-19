package com.example.todo.di.components

import com.example.todo.MainActivity
import com.example.todo.di.modules.MainViewModelModule
import com.example.todo.di.scopes.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [MainViewModelModule::class])
interface MainActivityComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create():MainActivityComponent
    }
    fun inject(mainActivity: MainActivity)
}