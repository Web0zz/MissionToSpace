package com.web0zz.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.cache.util.Constants.LAUNCHES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunches(launcheEntities: List<LaunchesEntity>)

    @Query("SELECT * FROM '$LAUNCHES_TABLE' WHERE id=:launchesId")
    fun getLaunche(launchesId: String): Flow<LaunchesEntity>
}