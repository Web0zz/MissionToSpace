package com.web0zz.cache.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.web0zz.cache.model.FailuresEntity

class FailuresEntityConverter {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: List<FailuresEntity>): String {
        val type = object : TypeToken<List<FailuresEntity>>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String): List<FailuresEntity> {
        val type = object : TypeToken<List<FailuresEntity>>() {}.type
        return gson.fromJson(toData, type)
    }
}