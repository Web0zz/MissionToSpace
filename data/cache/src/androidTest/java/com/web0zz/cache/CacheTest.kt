package com.web0zz.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.web0zz.cache.dao.LaunchesDao
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.cache.util.DataBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CacheTest {
    private lateinit var launchesDao: LaunchesDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        launchesDao = db.launchesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeLaunchesAndReadInList() = runBlocking {
        val launches: List<LaunchesEntity> = DataBuilder.createLaunches(3)

        launchesDao.insertLaunches(launches)
        val byId = launchesDao.getLaunches("0").first()

        assertThat(byId).isEqualTo(launches[0])
    }
}