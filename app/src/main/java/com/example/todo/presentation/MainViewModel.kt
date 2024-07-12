package com.example.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.repositories.TodoItemsRepository
import com.example.todo.domain.ToDoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MyViewModel {
    fun getItemList()
    fun addItem(
        importance: String,
        completed: Boolean,
        description: String,
        deadline: Long?
    )

    fun deleteItem(item: ToDoItem)
    fun checkCompleted()
    fun completeItem(item: ToDoItem)
    fun updateItem(
        id: String,
        importance: String,
        completed: Boolean,
        description: String,
        deadline: Long?,
        createdAt: Long
    )

    fun updateError(newValue: String)
    fun selectItem(item: ToDoItem)
    fun updateBDItemList()
    fun updateNetItemList()
    fun changeSelectedItem(item: ToDoItem)
    fun syncData()
}

class MainViewModel @Inject constructor(val repository: TodoItemsRepository) : ViewModel(),
    MyViewModel {

    val basicItem = ToDoItem("0", "basic", false, "", null, 0, 0, "MIIV")
    private val _selectedItem: MutableStateFlow<ToDoItem> =
        MutableStateFlow(basicItem)
    val selectedItem = _selectedItem.asStateFlow()
    private val _error = MutableStateFlow("Nothing")
    val error = _error.asStateFlow()
    override fun changeSelectedItem(item: ToDoItem) {
        _selectedItem.value = item
    }

    override fun addItem(
        importance: String,
        completed: Boolean,
        description: String,
        deadline: Long?
    ) {
        viewModelScope.launch {
            val newItem = ToDoItem(
                id = repository.generateID(),
                importance = importance,
                description = description,
                completed = completed,
                deadline = deadline,
                createdAt = repository.formatDate(),
                changedAt = repository.formatDate()
            )
            val result = repository.addItem(newItem)
            if (result.isSuccess) {
                getItemList()
            } else {
                _error.value =
                    ("Ошибка добавления нового дела: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun updateItem(
        id: String,
        importance: String,
        completed: Boolean,
        description: String,
        deadline: Long?,
        createdAt: Long
    ) {
        viewModelScope.launch {
            val item = ToDoItem(
                id = id,
                importance = importance,
                description = description,
                completed = completed,
                deadline = deadline,
                createdAt = createdAt,
                changedAt = repository.formatDate()
            )
            val result = repository.updateItem(item)
            if (result.isSuccess) {
                getItemList()
            } else {
                _error.value = ("Ошибка обновления дела: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun completeItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.completeItem(item)
            if (result.isSuccess) {
                getItemList()
            } else {
                _error.value = ("Ошибка завершения дела: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun deleteItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.deleteItem(item)
            if (result.isSuccess) {
                getItemList()
            } else {
                _error.value = ("Ошибка удаления дела: ${result.exceptionOrNull()?.message}")
            }

        }
    }

    override fun getItemList() {
        viewModelScope.launch {
            val result = repository.getItemList()
            if (!result.isSuccess) {
                _error.value = ("Ошибка получения списка дел: ${result.exceptionOrNull()?.message}")
            }
        }
    }


    override fun updateBDItemList() {
        viewModelScope.launch {
            val result = repository.updateBDItemList()
            if (result.isSuccess) {
            } else {
                _error.value = "Ошибка обновления списка дел: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    override fun updateNetItemList() {
        viewModelScope.launch {
            val result = repository.updateNetItemList()
            if (result.isSuccess) {
            } else {
                _error.value = "Ошибка обновления списка дел: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    override fun syncData() {
        viewModelScope.launch {
            val result = repository.syncData()
            if (result.isSuccess) {
            } else {
                _error.value = "Ошибка синхронизации данных: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    override fun checkCompleted() {
        viewModelScope.launch {
            val result = repository.checkCompleted()
            if (result.isSuccess) {
                getItemList()
            } else {
                _error.value =
                    ("Ошибка проверки выполненных заданий: ${result.exceptionOrNull()?.message}")
            }
        }
    }


    override fun updateError(newValue: String) {
        try {
            _error.value = newValue
        } catch (e: Exception) {
            _error.value = ("Основная ошибка.")
        }
    }

    override fun selectItem(item: ToDoItem) {
        try {
            _selectedItem.value = item
        } catch (e: Exception) {
            _error.value = ("Ошибка получения данных дела.")
        }
    }

}