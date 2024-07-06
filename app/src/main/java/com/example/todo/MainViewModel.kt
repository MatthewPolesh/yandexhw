package com.example.todo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface MyViewModel{
    fun getItemList(): MutableStateFlow<List<ToDoItem>>?
    fun addItem(item: ToDoItem)
    fun changeItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)
    fun checkCompleted()
    fun completeItem(item: ToDoItem)
    fun changeVisibility()
    fun saveNewItem(item: ToDoItem)
    fun getCompletedItemsCount ()
    fun updateError(newValue: String)

}
class MainViewModel:ViewModel(), MyViewModel {

    private val repository: TodoItemsRepository = TodoItemsRepository()

    init {
        viewModelScope.launch { repository.checkCompleted() }
    }


    private val _visibleCompleted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val visibleCompleted = _visibleCompleted.asStateFlow()

    private val _itemsList: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(repository.itemsList.value)
    val itemsList = _itemsList.asStateFlow()

    private val _completedItemsCount: MutableStateFlow<Int> = MutableStateFlow(repository.completedItemsCount.value)
    val completedItemsCount = _completedItemsCount.asStateFlow()

    private val _error = MutableLiveData("Nothing")
    val error: LiveData<String> = _error


    override fun addItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.addItem(item)
            if (result.isSuccess){
            } else{
                _error.postValue("Ошибка добавления нового дела: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun getItemList(): MutableStateFlow<List<ToDoItem>>? {
        var tempArr: MutableStateFlow<List<ToDoItem>>? = null
        viewModelScope.launch {
            val result = repository.getItemList()
            if (result.isSuccess){
               tempArr = result.getOrNull()
                _itemsList.value = tempArr!!.value
            } else{
                _error.postValue("Ошибка получения спика дел: ${result.exceptionOrNull()?.message}")
                tempArr = null
            }
        }
        return tempArr
    }

    override fun changeItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.changeItem(item)
            if (result.isSuccess){
                getItemList()
            } else{
                _error.postValue("Ошибка изменения дела: ${result.exceptionOrNull()?.message}")
            }
        }

    }

    override fun saveNewItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.saveNewItem(item)
            if (result.isSuccess) {
                getItemList()
            } else {
                _error.postValue("Ошибка сохранения нового дела: ${result.exceptionOrNull()?.message}")
            }
        }

    }

    override fun checkCompleted() {
        viewModelScope.launch {
            val result = repository.checkCompleted()
            if (result.isSuccess) {
                getItemList()
                getCompletedItemsCount()
            } else {
                _error.postValue("Ошибка проверки выполненных заданий: ${result.exceptionOrNull()?.message}")
            }
        }
    }



    override fun deleteItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.deleteItem(item)
            if (result.isSuccess) {
                getItemList()
                getCompletedItemsCount()
            } else {
                _error.postValue("Ошибка удаления дела: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun completeItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.completeItem(item)
            if (result.isSuccess) {
                getItemList()
                getCompletedItemsCount()
            } else {
                _error.postValue("Ошибка завершения дела: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun changeVisibility() {
            try {
                _visibleCompleted.value = !_visibleCompleted.value
            } catch (e: Exception){
                _error.postValue("Ошибка изменения видимости дел: ${e.message}")
            }

    }

    override fun getCompletedItemsCount() {
        viewModelScope.launch {
            val result = repository.getCompletedItemsCount()
            if (result.isSuccess)
            {
                _completedItemsCount.value = result.getOrNull()!!.value
            } else{
                _error.postValue("Ошибка получения количества выполненных заданий: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun updateError(newValue: String) {
        try {
            _error.value = newValue
        } catch (e: Exception)
        {
            _error.postValue("Основная ошибка.")
        }
    }

}