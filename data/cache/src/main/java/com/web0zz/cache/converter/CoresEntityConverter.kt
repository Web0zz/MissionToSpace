package com.web0zz.cache.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.web0zz.cache.model.CoresEntity

class CoresEntityConverter {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: List<CoresEntity>): String {
        val type = object : TypeToken<List<CoresEntity>>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String): List<CoresEntity> {
        val type = object : TypeToken<List<CoresEntity>>() {}.type
        return gson.fromJson(toData, type)
    }
}
