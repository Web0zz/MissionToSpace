package com.web0zz.domain.repository

import com.github.michaelbull.result.Result
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.launches.Launches
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    suspend fun getLaunchesData(): Flow<Result<List<Launches>, Failure>>
    suspend fun getLaunchesById(launchesId: String): Flow<Result<Launches, Failure>>
}