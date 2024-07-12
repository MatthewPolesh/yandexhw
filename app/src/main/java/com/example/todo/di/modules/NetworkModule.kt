package com.example.todo.di.modules


import android.content.Context
import com.example.todo.data.network.Api
import com.example.todo.data.network.ApiService
import com.example.todo.data.network.NetworkUtils
import com.example.todo.data.network.SyncWorker
import com.example.todo.data.network.TokenProviderImpl
import com.example.todo.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    @ApplicationScope
    fun provideApiService (tokenProviderImpl: TokenProviderImpl) : ApiService{
        return ApiService(tokenProviderImpl)
    }
    @Provides
    @ApplicationScope
    fun provideTokenProvide(): TokenProviderImpl {
        return TokenProviderImpl()
    }
    @Provides
    @ApplicationScope
    fun provideNetworkUtils(context: Context): NetworkUtils{
        return NetworkUtils(context)
    }

}