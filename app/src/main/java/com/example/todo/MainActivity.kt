package com.example.todo

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.todo.Network.*
import com.example.todo.UIComponents.Navigation.NavGraph
import com.example.todo.UIComponents.Theme.AppTheme

class MainActivity : ComponentActivity() {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: NetworkCallback

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value == true }) {
                onPermissionsGranted()
            } else {
                onPermissionsDenied()
            }
        }

    private val mainViewModel: MainViewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavGraph(navHostController = navController, mainViewModel)
            }
        }

        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Показываем объяснение, почему нужно разрешение
            showPermissionExplanation()
        } else {
            // Разрешения уже предоставлены
            onPermissionsGranted()
        }
    }

    private fun showPermissionExplanation() {
        AlertDialog.Builder(this)
            .setTitle("Permissions Required")
            .setMessage("This app requires Internet and Network State permissions to function properly.")
            .setPositiveButton("Grant") { dialog, _ ->
                dialog.dismiss()
                requestPermissions()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
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

    private fun onPermissionsGranted() {
        setupPeriodicWork(this)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = NetworkCallback {
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun onPermissionsDenied() {
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
