package com.web0zz.network.util

import java.net.InetSocketAddress
import java.net.Socket

object InternetAvailability {

    fun check(): Boolean =
        try {
            val socket = Socket()
            socket.connect(InetSocketAddress("8.8.8.8", 53))
            socket.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
}