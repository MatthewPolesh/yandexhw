package com.example.todo

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todo.data.network.NetworkUtils
import com.example.todo.data.network.SyncWorker
import com.example.todo.presentation.MainViewModel
import com.example.todo.presentation.uicomponents.ErrorDialog
import com.example.todo.presentation.uicomponents.navigation.NavGraph
import com.example.todo.presentation.uicomponents.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkUtils: NetworkUtils

    @Inject
    lateinit var mainViewModel: MainViewModel


    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                onPermissionsGranted()
            } else {
                onPermissionsDenied()
            }
        }


    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComp = (applicationContext as MyApp).appComponent
        val activityComp = appComp.activityComponent().create()
        activityComp.inject(this)
        mainViewModel.getThemeSettings(this)

        setContent {
            val navController = rememberNavController()
            NavGraph(navHostController = navController, mainViewModel, this)
        }
        networkUtils = NetworkUtils(applicationContext as MyApp)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                networkUtils.isNetworkAvailable.collectLatest { isConnected ->
                    if (isConnected) {
                        mainViewModel.repository.changeNetworkConnection(isConnected)
                        mainViewModel.syncData()
                    } else {
                        mainViewModel.repository.changeNetworkConnection(isConnected)
                        mainViewModel.getItemList()
                    }
                }
            }
        }

        //TODO("Переделать permissions")
        checkPermissions()
        //setupPeriodicWork()
    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showPermissionExplanation()
        } else {
            onPermissionsGranted()
        }
    }

    private fun showPermissionExplanation() {
        AlertDialog.Builder(this)
            .setTitle("Требуются разрешения")
            .setMessage("Это приложение требует разрешений на доступ к Интернету.")
            .setPositiveButton("Разрешить") { dialog, _ ->
                dialog.dismiss()
                requestPermissions()
            }
            .setNegativeButton("Отменить") { dialog, _ ->
                dialog.dismiss()
                onPermissionsDenied()
            }
            .show()
    }

    private fun requestPermissions() {
        requestPermissionsLauncher.launch(
            arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
        )
    }


    private fun setupPeriodicWork() {
        val workManager = WorkManager.getInstance(this)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniquePeriodicWork(
            "SyncWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            syncRequest
        )
    }

    private fun onPermissionsGranted() {
    }

    private fun onPermissionsDenied() {
    }

    override fun onDestroy() {
        super.onDestroy()
        networkUtils.unregisterCallback()

    }
}
