package com.example.todo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext


interface Repository {
    suspend fun getItemList(): Result<MutableStateFlow<List<ToDoItem>>>
    suspend fun addItem(item: ToDoItem): Result<Unit>
    suspend fun changeItem(item: ToDoItem): Result<Unit>
    suspend fun deleteItem(item: ToDoItem): Result<Unit>
    suspend fun checkCompleted(): Result<Unit>

    suspend fun completeItem(item: ToDoItem): Result<Unit>
    suspend fun saveNewItem(item: ToDoItem): Result<Unit>
    suspend fun getCompletedItemsCount(): Result<MutableStateFlow<Int>>
}

class TodoItemsRepository : Repository {
    private val _itemsList: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(itemList)
    val itemsList = _itemsList.asStateFlow()

    private val _completedItemsCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val completedItemsCount = _completedItemsCount.asStateFlow()


    override suspend fun addItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.Main) {
        try {
            var tempArr = emptyList<ToDoItem>().toMutableList()
            tempArr.add(item)
            _itemsList.value = tempArr
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }

    }

    override suspend fun getItemList(): Result<MutableStateFlow<List<ToDoItem>>> =
        withContext(Dispatchers.Main) {
            try {
                return@withContext Result.success(_itemsList)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    override suspend fun changeItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.Main) {
        try {
            val tempArr = _itemsList.value.toMutableList()
            val index = tempArr.indexOfFirst { it.id == item.id }
            if (index != -1) {
                val updatedItem = item.copy(completed = !item.completed)
                tempArr[index] = updatedItem
                _itemsList.value = tempArr
            }
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }

    }

    override suspend fun saveNewItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.Main) {
        try {

            val tempArr = _itemsList.value.toMutableList()
            if (item.description != "Лениться") {
                val index = tempArr.indexOfFirst { it.id == item.id }
                if (index != -1) {
                    val updatedItem = item.copy(completed = false)
                    tempArr[index] = updatedItem
                    _itemsList.value = tempArr
                } else {
                    tempArr.add(item)
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

    override suspend fun checkCompleted(): Result<Unit> = withContext(Dispatchers.Main) {
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


    override suspend fun deleteItem(item: ToDoItem): Result<Unit> = withContext(Dispatchers.Main) {
        try {
            var tempArr = _itemsList.value.toMutableList()
            tempArr.remove(item)
            _itemsList.value = tempArr
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    override suspend fun completeItem(item: ToDoItem): Result<Unit> =
        withContext(Dispatchers.Main) {
            try {
                var tempArr = _itemsList.value.toMutableList()
                val index = tempArr.indexOf(item)
                tempArr[index] = tempArr[index].copy(completed = true)
                _itemsList.value = tempArr
                checkCompleted()
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    override suspend fun getCompletedItemsCount(): Result<MutableStateFlow<Int>> =
        withContext(Dispatchers.Main) {
            try {
                return@withContext Result.success(_completedItemsCount)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

}