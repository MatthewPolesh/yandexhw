package com.example.todo.di.modules


import com.example.todo.data.repositories.TodoItemsRepository
import com.example.todo.di.scopes.ActivityScope
import com.example.todo.presentation.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainViewModelModule {
    @Provides
    @ActivityScope
    fun provideMainViewModel(repository: TodoItemsRepository): MainViewModel{
        return MainViewModel(repository)
    }
}