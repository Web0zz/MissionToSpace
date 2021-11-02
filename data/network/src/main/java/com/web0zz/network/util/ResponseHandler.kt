package com.web0zz.network.util

import retrofit2.Response

sealed class NetworkResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkResponse<T>()
    data class Error(val message: String) : NetworkResponse<Nothing>()
}

internal inline fun <T : Any> getResponse(call: () -> Response<T>): NetworkResponse<T> {
    val response: Response<T>
    try {
        response = call()
    } catch (t: Throwable) {
        return NetworkResponse.Error("Failed to request")
    }

    return if (!response.isSuccessful) {
        val errorBody = response.errorBody()

        // Api will never return large error body
        @Suppress("BlockingMethodInNonBlockingContext")
        NetworkResponse.Error(errorBody?.string() ?: "Failed")
    } else {
        return if (response.body() == null) {
            NetworkResponse.Error("Don't know what happened to data")
        } else {
            NetworkResponse.Success(response.body()!!)
        }
    }
}