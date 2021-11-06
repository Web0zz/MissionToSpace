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
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeLaunchesAndTakeAllLaunches() = runBlocking {
        val expectedLaunches: List<LaunchesEntity> = DataBuilder.createLaunches(3)

        launchesDao.insertLaunches(expectedLaunches)
        val returnedLaunches = launchesDao.getAllLaunches()

        assertThat(returnedLaunches).isEqualTo(expectedLaunches)
    }

    @Test
    fun writeLaunchesAndTakeLaunchesById() = runBlocking {
        val launches: List<LaunchesEntity> = DataBuilder.createLaunches(3)
        val expectedLaunchesById = launches.first()

        launchesDao.insertLaunches(launches)
        val returnedLaunchesById = launchesDao.getLaunchesById(expectedLaunchesById.id)

        assertThat(returnedLaunchesById).isEqualTo(expectedLaunchesById)
    }
}