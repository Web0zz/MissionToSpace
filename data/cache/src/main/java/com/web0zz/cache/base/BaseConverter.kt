package com.web0zz.cache.base

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class BaseConverter<T> {

    private val gson : Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: List<T>): String? {
        if (fromData.isEmpty()) return null

        val type = object : TypeToken<List<T>>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String?): List<T>? {
        if (toData.isNullOrEmpty()) return null

        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(toData, type)
    }
}