package com.example.todo.data.repositories

import com.example.todo.data.bd.RevisionDao
import com.example.todo.data.bd.ToDoItemDao
import com.example.todo.data.network.ApiService
import com.example.todo.data.network.NetworkUtils
import com.example.todo.domain.ItemWrapper
import com.example.todo.domain.ListWrapper
import com.example.todo.domain.Revision
import com.example.todo.domain.ToDoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.random.Random


class TodoItemsRepository @Inject constructor(
    apiService: ApiService,
    private val toDoItemDao: ToDoItemDao,
    private val revisionDao: RevisionDao,
    private val networkUtils: NetworkUtils
) {
    private val _networkConnection: MutableStateFlow<Boolean> =
        MutableStateFlow(networkUtils.isNetworkAvailable.value)
    private val api = apiService.api
    private val getApi = apiService.apiGet
    private val _itemsList: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())
    val itemsList = _itemsList.asStateFlow()
    private val _completedItemsCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val completedItemsCount = _completedItemsCount.asStateFlow()

    private val _revision: MutableStateFlow<Int> = MutableStateFlow(0)
    fun formatDate(): Long {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH::mm::ss")
        val formattedDate = currentDateTime.format(formatter)
        val parsedDateTime = LocalDateTime.parse(formattedDate, formatter)
        return parsedDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    fun generateID(): String {
        return Random.nextInt(1, Int.MAX_VALUE).toString()
    }

    fun changeNetworkConnection(connection: Boolean) {
        _networkConnection.value = connection
    }

    suspend fun addItem(newItem: ToDoItem): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            toDoItemDao.addItem(newItem)
            _itemsList.value = toDoItemDao.getItemList()
            checkCompleted()
            addItemToNet(newItem)

        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    private suspend fun addItemToNet(newItem: ToDoItem): Result<Unit> =
        withContext(Dispatchers.IO) {
            if (_networkConnection.value) {
                getRevision()
                val wrapper = ItemWrapper(newItem)
                val response = api.addItem(_revision.value, wrapper)
                if (response.isSuccessful) {
                    revisionDao.deleteAll()
                    revisionDao.addRevision(Revision(_revision.value + 1, false))
                    Result.success(Unit)
                } else {
                    return@withContext Result.failure(Exception("Ошибка добавления дела на сервер: ${response.message()}"))
                }
            } else {
                revisionDao.deleteAll()
                revisionDao.addRevision(Revision(_revision.value + 1, true))
                return@withContext Result.failure(Exception("Нет доступа к интернету."))
            }
        }

    suspend fun updateItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            toDoItemDao.updateItem(item)
            _itemsList.value = toDoItemDao.getItemList()
            checkCompleted()
            updateItemToNet(item)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    private suspend fun updateItemToNet(item: ToDoItem): Result<Unit> =
        withContext(Dispatchers.IO) {
            if (_networkConnection.value) {
                getRevision()
                val wrapper = ItemWrapper(item)
                val response = api.updateItem(_revision.value, item.id, wrapper)
                if (response.isSuccessful) {
                    revisionDao.deleteAll()
                    revisionDao.addRevision(Revision(_revision.value + 1, false))
                    return@withContext Result.success(Unit)
                } else {
                    return@withContext Result.failure(
                        Exception("Ошибка обновления дела на сервере: ${response.message()}")
                    )
                }
            } else {
                revisionDao.deleteAll()
                revisionDao.addRevision(Revision(_revision.value + 1, true))
                return@withContext Result.failure(Exception("Нет доступа к интернету."))
            }
        }

    suspend fun completeItem(item: ToDoItem): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val newItem = item.copy(completed = !item.completed)
                updateItem(newItem)
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    suspend fun deleteItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            toDoItemDao.deleteItem(item)
            _itemsList.value = toDoItemDao.getItemList()
            checkCompleted()
            if (_networkConnection.value) {
                getRevision()
                val response = api.deleteItem(_revision.value, item.id)
                if (response.isSuccessful) {
                    revisionDao.deleteAll()
                    revisionDao.addRevision(Revision(_revision.value + 1, false))
                    return@withContext Result.success(Unit)
                } else {
                    return@withContext Result.failure(Exception("Ошибка удаления дела с сервера: ${response.message()}"))
                }
            } else {
                revisionDao.deleteAll()
                revisionDao.addRevision(Revision(_revision.value + 1, true))
                return@withContext Result.failure(Exception("Нет доступа к интернету."))
            }
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    suspend fun getItemList(): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                _itemsList.value = toDoItemDao.getItemList()
                checkCompleted()
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    suspend fun updateBDItemList(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            toDoItemDao.updateList(getNetItemList().getOrNull()!!)
            getItemList()
            checkCompleted()
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    suspend fun updateNetItemList(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (_networkConnection.value) {
                getRevision()
                getItemList()
                val wrapper = ListWrapper(_itemsList.value)
                val response = api.updateList(_revision.value, wrapper)
                if (response.isSuccessful)
                    Result.success(Unit)
                else
                    Result.failure(Exception("Ошибка обновления списка на сервере: ${response.message()}"))
            } else {
                revisionDao.deleteAll()
                revisionDao.addRevision(Revision(_revision.value, true))
                return@withContext Result.failure(Exception("Нет доступа к интернету."))
            }
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    suspend fun checkCompleted(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            var tempCounter = 0
            for (item in itemsList.value) {
                if (item.completed)
                    tempCounter++
            }
            _completedItemsCount.value = tempCounter
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }


    private suspend fun getRevision(): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                if (_networkConnection.value) {
                    val response = getApi.getRevision()
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        _revision.value = apiResponse?.revision!!
                        revisionDao.deleteAll()
                        revisionDao.addRevision(Revision(_revision.value, false))
                        return@withContext Result.success(Unit)
                    } else {
                        return@withContext Result.failure(Exception("Ошибка получения ревизии с сервера: ${response.message()}"))
                    }
                } else {
                    return@withContext Result.failure(Exception("Нет доступа к интернету."))
                }
            } catch (e: HttpException) {
                return@withContext Result.failure(Exception("Ошибка получения ревизии с сервера: ${e.message()}"))
            } catch (e: Exception) {
                return@withContext Result.failure(Exception("Ошибка получения ревизии с сервера: ${e.message}"))
            }
        }

    private suspend fun getNetItemList(): Result<List<ToDoItem>> =
        withContext(Dispatchers.IO) {
            try {
                if (_networkConnection.value) {
                    val response = getApi.getItemList()
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        return@withContext Result.success(apiResponse?.list ?: emptyList())
                    } else {
                        return@withContext Result.failure(Exception("Ошибка получения дел с сервера: ${response.message()}"))
                    }
                } else {
                    return@withContext Result.failure(Exception("Нет доступа к интернету."))
                }
            } catch (e: HttpException) {
                return@withContext Result.failure(Exception("Ошибка получения дел с сервера: ${e.message()}"))
            } catch (e: Exception) {
                return@withContext Result.failure(Exception("Ошибка получения дел с сервера: ${e.message}"))
            }
        }

    suspend fun syncData(): Result<Unit> =
        withContext(Dispatchers.IO) {
            if (revisionDao.getRevision() != null) {
                try {
                    val currentRevision = revisionDao.getRevision()!!.revision
                    val unSyncWork = revisionDao.getRevision()!!.unSyncWork
                    getRevision()
                    if (_revision.value != currentRevision) {
                        val response = getNetItemList()
                        val netItemList = response.getOrNull() ?: emptyList()
                        if (unSyncWork) {
                            findDifferences(_itemsList.value, netItemList)
                        } else {
                            updateBDItemList()
                        }
                        return@withContext Result.success(Unit)
                    } else {
                        if (unSyncWork) {
                            val response = getNetItemList()
                            val netItemList = response.getOrNull() ?: emptyList()
                            findDifferences(_itemsList.value, netItemList)
                        } else {
                            updateBDItemList()
                        }
                        return@withContext Result.success(Unit)
                    }
                } catch (e: Exception) {
                    return@withContext Result.failure(Exception("Неудалось синхронизировать данные."))
                }
            } else {
                getItemList()
                getRevision()
                Result.success(Unit)
            }
        }

    private suspend fun findDifferences(listBD: List<ToDoItem>, listNet: List<ToDoItem>) =
        withContext(Dispatchers.IO) {
            val map1 = listBD.associateBy { it.id }
            val map2 = listNet.associateBy { it.id }
            val allIds = map1.keys + map2.keys
            for (id in allIds) {
                val itemBD = map1[id]
                val itemNet = map2[id]
                if (itemBD != itemNet) {
                    if ((itemBD != null) && (itemNet != null)) {
                        if (itemBD.changedAt!! > itemNet.changedAt!!) {
                            updateItem(itemBD)
                        } else {
                            toDoItemDao.updateItem(itemNet)
                            _itemsList.value = toDoItemDao.getItemList()
                        }
                    } else {
                        if (itemBD == null) {
                            itemNet?.let { toDoItemDao.addItem(it) }
                        }
                        if (itemNet == null) {
                            itemBD?.let { addItemToNet(it) }
                        }
                    }
                }
            }
        }
}