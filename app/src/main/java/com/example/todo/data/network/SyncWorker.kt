package com.example.todo.data.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todo.data.repositories.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: TodoItemsRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}
