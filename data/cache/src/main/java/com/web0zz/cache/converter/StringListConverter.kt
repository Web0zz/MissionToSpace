package com.web0zz.cache.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(toData, type)
    }
}