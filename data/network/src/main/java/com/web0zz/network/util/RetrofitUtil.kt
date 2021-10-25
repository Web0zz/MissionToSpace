package com.web0zz.network.util

import com.web0zz.network.exception.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

// TODO need to test this function
inline fun <reified T> Response<T>.getResponse(): T {
    val responseBody = body()
    return if (this.isSuccessful && responseBody != null) {
        responseBody
    } else {
        val errorResponse = errorBody()?.string()
        val message = StringBuilder()
        errorResponse.let {
            try {
                message.append(JSONObject(it!!).getString("status_message"))
            } catch (e: JSONException) {}
        }
        throw ApiException(message.toString())
    }
}