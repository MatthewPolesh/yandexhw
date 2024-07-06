package com.example.todo.Network

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

fun setupPeriodicWork(context: Context) {
    val workRequest: WorkRequest = PeriodicWorkRequestBuilder<FetchTasksWorker>(8, TimeUnit.HOURS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "FetchTasksWork",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest as PeriodicWorkRequest
    )
}
