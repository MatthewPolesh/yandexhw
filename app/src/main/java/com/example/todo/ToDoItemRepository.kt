package com.example.todo

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


interface Repository {
    fun getItemList(): List<ToDoItem>
    fun addItem(item: ToDoItem)
    fun changeItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)
    fun checkCompleted()
    fun changeCounter(item: ToDoItem)
    fun completeItem(item: ToDoItem)
    fun changeVisibility()
    fun saveNewItem(item: ToDoItem)
}

class TodoItemsRepository : Repository {
    private val _itemsList: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(itemList)
    val itemsList = _itemsList.asStateFlow()

    private val _completedItemsCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val completedItemsCount = _completedItemsCount.asStateFlow()

    private val _visibleCompleted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val visibleCompleted = _visibleCompleted.asStateFlow()

    override fun addItem(item: ToDoItem) {
        var tempArr = emptyList<ToDoItem>().toMutableList()
        tempArr.add(item)
        _itemsList.value = tempArr
    }

    override fun getItemList(): List<ToDoItem> {
        return itemsList.value
    }

    override fun changeItem(item: ToDoItem) {
        var tempArr = _itemsList.value.toMutableList()
        val index = tempArr.indexOfFirst { it.id == item.id }
        if (index != -1) {
            val updatedItem = item.copy(completed = !item.completed)
            tempArr[index] = updatedItem
            _itemsList.value = tempArr
        }
    }

    override fun saveNewItem(item: ToDoItem) {
        var tempArr = _itemsList.value.toMutableList()
        val index = tempArr.indexOfFirst { it.id == item.id }
        if (index != -1) {
            val updatedItem = item.copy(completed = false)
            tempArr[index] = updatedItem
            _itemsList.value = tempArr
        }
        else
        {
            tempArr.add(item)
            _itemsList.value = tempArr
        }
        changeCounter(item)
    }

    override fun checkCompleted() {
        var tempCounter = 0
        for (item in itemsList.value) {
            if (item.completed)
                tempCounter++
        }
        _completedItemsCount.value = tempCounter
    }

    override fun changeCounter(item: ToDoItem) {
        var tempCounter = _completedItemsCount.value
        if (item.completed) {
            tempCounter++
        } else
            tempCounter--
        _completedItemsCount.value = tempCounter
        checkCompleted()

    }

    override fun deleteItem(item: ToDoItem) {

        var tempArr = _itemsList.value.toMutableList()
        Log.d("MyTag","$tempArr")
        tempArr.removeIf{ it === item }
        Log.d("MyTag","$tempArr")
        _itemsList.value = tempArr


    }

    override fun completeItem(item: ToDoItem) {
        var tempArr = _itemsList.value.toMutableList()
        val index = tempArr.indexOf(item)
        tempArr[index] = tempArr[index].copy(completed = true)
        _itemsList.value = tempArr
        changeCounter(item)
    }

    override fun changeVisibility() {
        _visibleCompleted.value = !_visibleCompleted.value
        val tempArr = _itemsList.value
        _itemsList.value = tempArr
    }

}