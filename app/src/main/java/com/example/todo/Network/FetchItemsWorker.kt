package com.example.todo.Network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todo.MainViewModel

class FetchTasksWorker(
    context: Context,
    workerParams: WorkerParameters,
    viewModel: MainViewModel
) : CoroutineWorker(context, workerParams) {

    private val repository = viewModel.networkRepository
    private val itemlist = viewModel.repository.itemsList.value

    override suspend fun doWork(): Result {
        return try {
            repository.updateItemList(itemlist)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
