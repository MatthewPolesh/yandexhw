package com.example.todo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.absoluteValue
import kotlin.random.Random




class TodoItemsRepository  {
    private val itemList = mutableListOf(
        ToDoItem("100","basic",false,"Мое дело 1",null,0,0),
        ToDoItem("101","low",false,"Мое дело 2",null,0,0),
        ToDoItem("102","important",false,"Мое дело 3",null,0,0),
        ToDoItem("103","basic",false,"Мое дело 4",null,0,0),
        ToDoItem("104","basic",false,"Мое дело 5",null,0,0),
        ToDoItem("105","important",false,"Мое дело 6",null,0,0),
        ToDoItem("106","basic",false,"Мое дело 7",null,0,0),
        )

    private val _itemsList: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(itemList)
    val itemsList = _itemsList.asStateFlow()

    private val _completedItemsCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val completedItemsCount = _completedItemsCount.asStateFlow()


    suspend fun addItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.Main) {
        try {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate = currentDate.format(formatter)
            val parsedDateTime = LocalDate.parse(formattedDate, formatter).atStartOfDay()
            val milliseconds = parsedDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            var tempArr = _itemsList.value.toMutableList()
            tempArr.add(item.copy(id = ThreadLocalRandom.current().nextInt().toString(), created_at = milliseconds, changed_at = milliseconds))
            _itemsList.value = tempArr
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }

    }

    suspend fun getItemList(): Result<List<ToDoItem>> =
        withContext(Dispatchers.Main) {
            try {
                return@withContext Result.success(_itemsList.value)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    suspend fun updateItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.Main) {
        try {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate = currentDate.format(formatter)
            val parsedDateTime = LocalDate.parse(formattedDate, formatter).atStartOfDay()
            val milliseconds = parsedDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            val tempArr = _itemsList.value.toMutableList()
            if (item.description != "Лениться") {
                val index = tempArr.indexOfFirst { it.id == item.id }
                if (index != -1) {
                    val updatedItem = item.copy(completed = false, changed_at = milliseconds)
                    tempArr[index] = updatedItem
                    _itemsList.value = tempArr
                }
            } else {
                return@withContext Result.failure(Exception("Лениться запрещено!"))
            }
            checkCompleted()
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)

        }
    }

    suspend fun checkCompleted(): Result<Unit> = withContext(Dispatchers.Main) {
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


    suspend fun deleteItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.Main) {
        try {
            var tempArr = _itemsList.value.toMutableList()
            tempArr.remove(item)
            _itemsList.value = tempArr
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    suspend fun completeItem(item: ToDoItem): Result<Unit> =
        withContext(Dispatchers.Main) {
            try {
                var tempArr = _itemsList.value.toMutableList()
                val index = tempArr.indexOf(item)
                tempArr[index] = tempArr[index].copy(completed = !item.completed)
                _itemsList.value = tempArr
                checkCompleted()
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    suspend fun getCompletedItemsCount(): Result<MutableStateFlow<Int>> =
        withContext(Dispatchers.Main) {
            try {
                return@withContext Result.success(_completedItemsCount)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

}