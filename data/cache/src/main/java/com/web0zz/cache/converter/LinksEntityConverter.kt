package com.web0zz.cache.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.web0zz.cache.model.LinksEntity

class LinksEntityConverter {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: LinksEntity): String {
        val type = object : TypeToken<LinksEntity>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String): LinksEntity {
        val type = object : TypeToken<LinksEntity>() {}.type
        return gson.fromJson(toData, type)
    }
}

class PatchEntityConverter {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: LinksEntity.Companion.PatchEntity): String {
        val type = object : TypeToken<LinksEntity.Companion.PatchEntity>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String): LinksEntity.Companion.PatchEntity {
        val type = object : TypeToken<LinksEntity.Companion.PatchEntity>() {}.type
        return gson.fromJson(toData, type)
    }
}

class RedditEntityConverter {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: LinksEntity.Companion.RedditEntity): String {
        val type = object : TypeToken<LinksEntity.Companion.RedditEntity>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String): LinksEntity.Companion.RedditEntity {
        val type = object : TypeToken<LinksEntity.Companion.RedditEntity>() {}.type
        return gson.fromJson(toData, type)
    }
}

class FlickrEntityConverter {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun from(fromData: LinksEntity.Companion.FlickrEntity): String {
        val type = object : TypeToken<LinksEntity.Companion.FlickrEntity>() {}.type
        return gson.toJson(fromData, type)
    }

    @TypeConverter
    fun to(toData: String): LinksEntity.Companion.FlickrEntity {
        val type = object : TypeToken<LinksEntity.Companion.FlickrEntity>() {}.type
        return gson.fromJson(toData, type)
    }
}