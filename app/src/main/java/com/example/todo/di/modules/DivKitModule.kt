package com.example.todo.di.modules

import android.content.Context
import com.example.todo.di.scopes.ActivityScope
import com.example.todo.presentation.uicomponents.divkit.MyTypefaceProvider
import com.example.todo.presentation.uicomponents.divkit.NavigationDivActionHandler
import dagger.Module
import dagger.Provides

@Module
class DivKitModule {
    @Provides
    @ActivityScope
    fun provideMyTypefaceProvider(context: Context): MyTypefaceProvider {
        return MyTypefaceProvider(context = context)
    }

    @Provides
    @ActivityScope
    fun provideNavigationDivActionHandler(): NavigationDivActionHandler {
        return NavigationDivActionHandler()
    }
}