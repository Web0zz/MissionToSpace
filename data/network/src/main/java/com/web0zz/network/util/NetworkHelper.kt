package com.web0zz.network.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object UnAvailable : NetworkStatus()
}

class NetworkHelper @Inject constructor(
    appContext: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    private var connectivityManager: ConnectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var validNetworkConnections: Boolean = false
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    private fun getConnectivityManagerCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            val hasNetworkConnection = networkCapability
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

            if (hasNetworkConnection) {
                determineInternetAccess()
            }
        }
    }

    private fun determineInternetAccess() {
        coroutineScope.launch {
            if (InternetAvailability.check()) {
                withContext(Dispatchers.Main) {
                    validNetworkConnections = true
                }
            }
        }
    }

    fun getConnectionStatus(): NetworkStatus {
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)

        val status = when (validNetworkConnections) {
            true -> NetworkStatus.Available
            false -> NetworkStatus.UnAvailable
        }

        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
        return status
    }
}