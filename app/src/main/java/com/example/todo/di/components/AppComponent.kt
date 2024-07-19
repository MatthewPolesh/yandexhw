package com.example.todo.di.components

import android.content.Context
import com.example.todo.data.network.SyncWorker
import com.example.todo.di.modules.NetworkModule
import com.example.todo.di.modules.DBModule
import com.example.todo.di.modules.DivKitModule
import com.example.todo.di.scopes.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DBModule::class, NetworkModule::class, DivKitModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create (@BindsInstance context: Context): AppComponent
    }
    fun activityComponent(): MainActivityComponent.Factory
}

