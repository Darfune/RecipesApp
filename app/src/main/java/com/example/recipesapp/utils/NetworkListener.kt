package com.example.recipesapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow


class NetworkListener : ConnectivityManager.NetworkCallback() {

    private var isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network =
            connectivityManager.activeNetwork // network is currently in a high power state for performing data transmission.
        Log.d("Network", "active network $network")
        if (network == null) { // return false if network is null
            isNetworkAvailable.value = false
            return isNetworkAvailable
        }

        val capabilities = connectivityManager.getNetworkCapabilities(network)

        if (capabilities == null) { // return false if Network Capabilities is null
            isNetworkAvailable.value = false
            return isNetworkAvailable
        }

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { // check if wifi is connected
                Log.d("NetworkListener", "wifi connected")
                isNetworkAvailable.value = true
                isNetworkAvailable
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { // check if mobile dats is connected
                Log.d("NetworkListener", "cellular network connected")
                isNetworkAvailable.value = true
                isNetworkAvailable
            }
            else -> {
                Log.d("NetworkListener", "internet not connected")
                isNetworkAvailable.value = false
                isNetworkAvailable
            }
        }
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }
}
