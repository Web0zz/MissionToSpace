package com.web0zz.repository.repository

import com.github.michaelbull.result.Result
import com.web0zz.cache.AppDatabase
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository
import com.web0zz.network.SpaceXService
import com.web0zz.repository.mapper.DataMappersFacade
import javax.inject.Inject

class LaunchesRepositoryImp @Inject constructor(
    private val apiService: SpaceXService,
    private val appDatabase: AppDatabase,
    private val dataMappersFacade: DataMappersFacade
) : LaunchesRepository {
    override fun getLaunchesData(): Result<Failure, List<Launches>> {
        TODO("Not yet implemented")
    }

    override fun getLaunchesById(): Result<Failure, Launches> {
        TODO("Not yet implemented")
    }
}