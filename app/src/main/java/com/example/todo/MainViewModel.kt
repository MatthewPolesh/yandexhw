package com.example.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.Network.ApiService
import com.example.todo.Network.NetworkRepository
import com.example.todo.Network.TokenProviderImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface MyViewModel {
    fun getItemList()
    fun addItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)
    fun checkCompleted()
    fun completeItem(item: ToDoItem)
    fun changeVisibility()
    fun updateItem(item: ToDoItem)
    fun getCompletedItemsCount()
    fun updateError(newValue: String)
    fun selectItem( item: ToDoItem)
    fun updateItemList(itemList: List<ToDoItem>)


}

class MainViewModel() : ViewModel(), MyViewModel {

private val tokenProviderImpl = TokenProviderImpl()
    private val apiService = ApiService(tokenProviderImpl)
    val networkRepository: NetworkRepository = NetworkRepository(apiService.api, apiService.apiGet)
    val repository: TodoItemsRepository = TodoItemsRepository()

    init {
        viewModelScope.launch {
            repository.checkCompleted()
            repository.getItemList()
            repository.getItemList().getOrNull()?.let { updateItemList(it) }
        }
    }

    private val _visibleCompleted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val visibleCompleted = _visibleCompleted.asStateFlow()

    private val _itemsList: MutableStateFlow<List<ToDoItem>> =
        MutableStateFlow(repository.itemsList.value)
    val itemsList = _itemsList.asStateFlow()

    val basicItem = ToDoItem("0", "basic", false, "", null, 0, 0,"1")
    private val _selectedItem: MutableStateFlow<ToDoItem> =
        MutableStateFlow(basicItem)
    val selectedItem = _selectedItem.asStateFlow()

    private val _completedItemsCount: MutableStateFlow<Int> =
        MutableStateFlow(repository.completedItemsCount.value)
    val completedItemsCount = _completedItemsCount.asStateFlow()

    private val _error = MutableStateFlow("Nothing")
    val error  = _error.asStateFlow()


    override fun addItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.addItem(item)
            if (result.isSuccess) {
                getItemList()
                val networkResult = networkRepository.addItem(item)
                if (networkResult.isFailure) {
                    _error.value = "Ошибка добавления задачи: ${networkResult.exceptionOrNull()?.message}"
                }
            } else {
                _error.value = ("Ошибка добавления нового дела: ${result.exceptionOrNull()?.message}")
            }


        }
    }

    override fun getItemList() {
        var tempArr: List<ToDoItem>?
        viewModelScope.launch {
            val result = repository.getItemList()
            if (result.isSuccess) {
                tempArr = result.getOrNull()
                _itemsList.value = tempArr!!
            } else {
                _error.value = ("Ошибка получения спика дел: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun updateItemList(itemList: List<ToDoItem>) {
        viewModelScope.launch {
            val networkResult = networkRepository.updateItemList(itemList)
            if (networkResult.isSuccess) {
            } else {
                _error.value = "Ошибка получения спика дел: ${networkResult.exceptionOrNull()?.message}"
            }
        }

    }


    override fun updateItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.updateItem(item)
            if (result.isSuccess) {
                getItemList()
                val networkResult = networkRepository.updateItem(item.id, item)
                if (networkResult.isFailure) {
                    _error.value = "Ошибка обновления задачи: ${networkResult.exceptionOrNull()?.message}"
                }else{
                }
            } else {
                _error.value = ("Ошибка сохранения нового дела: ${result.exceptionOrNull()?.message}")
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
                _error.value = ("Ошибка проверки выполненных заданий: ${result.exceptionOrNull()?.message}")
            }
        }
    }


    override fun deleteItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.deleteItem(item)
            if (result.isSuccess) {
                getItemList()
                getCompletedItemsCount()

                val networkResult = networkRepository.deleteItem(item.id)
                if (networkResult.isFailure) {
                    _error.value = "Ошибка удаления задачи: ${networkResult.exceptionOrNull()?.message}"
                }
            } else {
                _error.value = ("Ошибка удаления дела: ${result.exceptionOrNull()?.message}")
            }

        }
    }

    override fun completeItem(item: ToDoItem) {
        viewModelScope.launch {
            val result = repository.completeItem(item)
            if (result.isSuccess) {
                networkRepository.updateItem(item.id,item.copy(completed = !item.completed))
                getItemList()
                getCompletedItemsCount()
            } else {
                _error.value = ("Ошибка завершения дела: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    override fun changeVisibility() {
        try {
            _visibleCompleted.value = !_visibleCompleted.value
        } catch (e: Exception) {
            _error.value = ("Ошибка изменения видимости дел: ${e.message}")
        }

    }

    override fun getCompletedItemsCount() {
        viewModelScope.launch {
            val result = repository.getCompletedItemsCount()
            if (result.isSuccess) {
                _completedItemsCount.value = result.getOrNull()!!.value
            } else {
                _error.value = ("Ошибка получения количества выполненных заданий: ${result.exceptionOrNull()?.message}")
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
        } catch (e: Exception){
            _error.value = ("Ошибка получения данных дела.")
        }
    }

}