package com.web0zz.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.web0zz.cache.converter.*
import com.web0zz.cache.dao.LaunchesDao
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.cache.util.Constants.DB_VERSION

@TypeConverters(
    CoresEntityConverter::class,
    FailuresEntityConverter::class,
    FairingsEntityConverter::class,
    LinksEntityConverter::class,
    PatchEntityConverter::class,
    RedditEntityConverter::class,
    FlickrEntityConverter::class
)
@Database(
    entities = [LaunchesEntity::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun launchesDao(): LaunchesDao
}