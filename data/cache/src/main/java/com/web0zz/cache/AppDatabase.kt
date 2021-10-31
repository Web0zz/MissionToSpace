package com.web0zz.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.web0zz.cache.converter.*
import com.web0zz.cache.dao.LaunchesDao
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.cache.util.Constants.DB_NAME
import com.web0zz.cache.util.Constants.DB_VERSION

@TypeConverters(
    CoresEntityConverter::class,
    FailuresEntityConverter::class,
    FairingsEntityConverter::class,
    LinksEntityConverter::class,
    PatchEntityConverter::class,
    RedditEntityConverter::class,
    FlickrEntityConverter::class,
    StringListConverter::class
)
@Database(
    entities = [LaunchesEntity::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun launchesDao(): LaunchesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}