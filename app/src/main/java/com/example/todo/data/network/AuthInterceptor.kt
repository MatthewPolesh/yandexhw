package com.example.todo.data.network


import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", tokenProvider.getToken())
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}

interface TokenProvider {
    fun getToken(): String
}
