package com.web0zz.cache.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.web0zz.cache.model.FairingsEntity

class FairingsEntityConverter {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: FairingsEntity): String {
        val type = object : TypeToken<FairingsEntity>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String): FairingsEntity {
        val type = object : TypeToken<FairingsEntity>() {}.type
        return gson.fromJson(toData, type)
    }
}
