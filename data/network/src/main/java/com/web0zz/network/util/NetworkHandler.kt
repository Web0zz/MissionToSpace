package com.web0zz.network.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import javax.inject.Inject

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object UnAvailable : NetworkStatus()
}

class NetworkHandler @Inject constructor(
    appContext: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : LiveData<NetworkStatus>() {

    private var connectivityManager: ConnectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val validNetworkConnections : ArrayList<Network> = ArrayList()
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    private fun announceStatus() {
        when(validNetworkConnections.isNotEmpty()) {
            true -> postValue(NetworkStatus.Available)
            false -> postValue(NetworkStatus.UnAvailable)
        }
    }

    private fun getConnectivityManagerCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            val hasNetworkConnection =
                networkCapability
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

            if (hasNetworkConnection){
                determineInternetAccess(network)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            validNetworkConnections.remove(network)
            announceStatus()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            when (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                true -> determineInternetAccess(network)
                else -> validNetworkConnections.remove(network)
            }
            announceStatus()
        }
    }

    private fun determineInternetAccess(network: Network) {
        coroutineScope.launch{
            if (InternetAvailability.check()){
                withContext(Dispatchers.Main){
                    validNetworkConnections.add(network)
                    announceStatus()
                }
            }
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }
}