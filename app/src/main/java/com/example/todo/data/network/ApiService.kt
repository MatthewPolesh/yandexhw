package com.example.todo.data.network

import com.example.todo.domain.ItemWrapper
import com.example.todo.domain.ListWrapper
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import javax.inject.Inject

class ApiService (tokenProvider: TokenProvider) {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenProvider))
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofitGet = Retrofit.Builder()
        .baseUrl("https://hive.mrdekk.ru/todo/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hive.mrdekk.ru/todo/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: Api = retrofit.create(Api::class.java)
    val apiGet: ApiGet = retrofitGet.create(ApiGet::class.java)
}

interface Api {
    @POST("list")
    suspend fun addItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body item: ItemWrapper
    ): Response<Void>

    @PATCH("list")
    suspend fun updateList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body list: ListWrapper
    ): Response<Void>

    @DELETE("list/{id}")
    suspend fun deleteItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): Response<Void>

    @PUT("list/{id}")
    suspend fun updateItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body item: ItemWrapper
    ): Response<Void>
}

interface ApiGet {
    @GET("list")
    suspend fun getItemList(): Response<ApiResponse>
    @GET("list")
    suspend fun getRevision(): Response<ApiResponse>


}
