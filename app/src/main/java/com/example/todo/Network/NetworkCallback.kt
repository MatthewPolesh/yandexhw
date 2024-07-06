package com.example.todo.Network

import android.net.ConnectivityManager
import android.net.Network

class NetworkCallback(private val onNetworkAvailable: () -> Unit) : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        onNetworkAvailable()
    }

}
