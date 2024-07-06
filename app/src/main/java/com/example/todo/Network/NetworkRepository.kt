package com.example.todo.Network

import android.util.Log
import com.example.todo.ItemWrapper
import com.example.todo.ListWrapper
import com.example.todo.ToDoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response


class NetworkRepository(private val api: Api, private val apiGet: ApiGet) {


    private val _itemsList: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())
    val itemsList = _itemsList.asStateFlow()
    private val _revision: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _error = MutableStateFlow<String?>(null)
    val error: MutableStateFlow<String?> get() = _error


    suspend fun getItemList(): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiGet.getItemList()
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    _itemsList.value = apiResponse?.list ?: emptyList()
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Ошибка получения дел с сервера: ${response.message()}"))
                }
            } catch (e: HttpException) {
                Result.failure(Exception("Failed to fetch tasks: ${e.message()}"))
            } catch (e: Exception) {
                Result.failure(Exception("Failed to fetch tasks: ${e.message}"))
            }
        }

    suspend fun getRevision(): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiGet.getRevision()
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    _revision.value = apiResponse?.revision!!
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Ошибка получения ревизии с сервера: ${response.message()}"))
                }
            } catch (e: HttpException) {
                Result.failure(Exception("Ошибка получения ревизии с сервера: ${e.message()}"))
            } catch (e: Exception) {
                Result.failure(Exception("Ошибка получения ревизии с сервера: ${e.message}"))
            }
        }

    suspend fun updateItemList(itemlist: List<ToDoItem>): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                getItemList()
                for (item in itemlist) {
                    val response:Response<Void>
                    getRevision()
                    val wrapper = ItemWrapper(item)
                    if(item in itemsList.value){
                        response = api.updateItem(_revision.value, item.id, wrapper)
                    }
                    else{
                        response = api.addItem(_revision.value, wrapper)
                    }

                    if (!response.isSuccessful) {
                        return@withContext Result.failure(Exception("Ошибка получения списка дел с сервера: ${response.message()}"))
                    }
                }
            Result.success(Unit)
            } catch (e: HttpException) {
                Result.failure(Exception("Ошибка получения списка дел с сервера: ${e.message()}"))
            } catch (e: Exception) {
                Result.failure(Exception("Ошибка получения списка дел с сервера: ${e.message}"))
            }
        }

    suspend fun addItem(item: ToDoItem): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                getRevision()
                val wrapper = ItemWrapper(item)
                val response = api.addItem(_revision.value, wrapper)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Ошибка добавления дела на сервер: ${response.message()}"))
                }
            } catch (e: HttpException) {
                Result.failure(Exception("Ошибка добавления дела на сервер: ${e.message()}"))
            } catch (e: Exception) {
                Result.failure(Exception("Ошибка добавления дела на сервер: ${e.message}"))
            }
        }

    suspend fun deleteItem(id: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                getRevision()
                val response = api.deleteItem(_revision.value, id)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Ошибка удаления дела с сервера: ${response.message()}"))
                }
            } catch (e: HttpException) {
                Result.failure(Exception("Ошибка удаления дела с сервера: ${e.message()}"))
            } catch (e: Exception) {
                Result.failure(Exception("Ошибка удаления дела с сервера: ${e.message}"))
            }
        }

    suspend fun updateItem(id: String, item: ToDoItem): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                getRevision()
                val wrapper = ItemWrapper(item)
                val response = api.updateItem(_revision.value, id, wrapper)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(
                        Exception("Ошибка обновления дела на сервере: ${response.message()}")
                    )
                }

            } catch (e: HttpException) {
                Result.failure(Exception("Ошибка обновления дела на сервере: ${e.message()}"))
            } catch (e: Exception) {
                Result.failure(Exception("Ошибка обновления дела на сервере: ${e.message}"))
            }
        }

}
