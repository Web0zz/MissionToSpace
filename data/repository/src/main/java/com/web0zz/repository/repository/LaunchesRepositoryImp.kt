package com.web0zz.repository.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.web0zz.cache.dao.LaunchesDao
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.launches.Launches
import com.web0zz.domain.repository.LaunchesRepository
import com.web0zz.network.SpaceXService
import com.web0zz.network.model.LaunchesDto
import com.web0zz.network.util.NetworkHelper
import com.web0zz.network.util.NetworkResponse
import com.web0zz.network.util.NetworkStatus
import com.web0zz.repository.mapper.DataMappersFacade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaunchesRepositoryImp @Inject constructor(
    private val apiService: SpaceXService,
    private val launchesDao: LaunchesDao,
    private val dataMappersFacade: DataMappersFacade,
    private val networkHelper: NetworkHelper
) : LaunchesRepository {

    override suspend fun getLaunchesData(): Flow<Result<List<Launches>, Failure>> =
        flow {
            lateinit var result: Result<List<Launches>, Failure>

            try {
                when (networkHelper.getConnectionStatus()) {
                    is NetworkStatus.Available -> {
                        val apiResponse: NetworkResponse<List<LaunchesDto>> =
                            apiService.getLaunches()

                        result = when (apiResponse) {
                            is NetworkResponse.Success -> {
                                val data =
                                    apiResponse.data.map { dataMappersFacade.launchesDtoMapper(it) }

                                launchesDao.insertLaunches(
                                    apiResponse.data.map {
                                        dataMappersFacade.launchesDtoToEntityMapper(it)
                                    }
                                )
                                Ok(data)
                            }
                            is NetworkResponse.Error -> {
                                val data = launchesDao.getAllLaunches()
                                    .map { dataMappersFacade.launchesEntityMapper(it) }

                                if (data.isEmpty()) {
                                    Err(Failure.ApiResponseError(apiResponse.message))
                                } else Ok(data)
                            }
                        }
                    }
                    is NetworkStatus.UnAvailable -> {
                        val data = launchesDao.getAllLaunches()
                            .map { dataMappersFacade.launchesEntityMapper(it) }

                        result = if (data.isEmpty()) {
                            Err(Failure.NetworkConnectionError("No network"))
                        } else Ok(data)
                    }
                }

                emit(result)
            } catch (e: Exception) {
                emit(Err(Failure.UnknownError(e.message ?: "Error")))
            }
            // TODO move catch bloc to extension of flow
        }

    override suspend fun getLaunchesById(launchesId: String): Flow<Result<Launches, Failure>> =
        flow {
            lateinit var result: Result<Launches, Failure>

            try {
                val cacheResponse: List<LaunchesEntity> = launchesDao.getLaunchesById(launchesId)

                result =
                    if (cacheResponse.isNotEmpty()) {
                        val data =
                            cacheResponse.first().let { dataMappersFacade.launchesEntityMapper(it) }

                        Ok(data)
                    } else {
                        when (networkHelper.getConnectionStatus()) {
                            is NetworkStatus.Available -> {
                                when (val apiResponse = apiService.getLaunchesById(launchesId)) {
                                    is NetworkResponse.Success -> {
                                        val data = apiResponse.data.let {
                                            dataMappersFacade.launchesDtoMapper(it)
                                        }

                                        launchesDao.insertLaunches(
                                            listOf(
                                                apiResponse.data.let {
                                                    dataMappersFacade.launchesDtoToEntityMapper(it)
                                                }
                                            )
                                        )

                                        Ok(data)
                                    }
                                    is NetworkResponse.Error -> {
                                        Err(Failure.ApiResponseError(apiResponse.message))
                                    }
                                }
                            }
                            is NetworkStatus.UnAvailable -> {
                                Err(Failure.NetworkConnectionError("Unavailable connection"))
                            }
                        }
                    }

                emit(result)
            } catch (e: Exception) {
                emit(Err(Failure.UnknownError(e.message ?: "Error")))
            }
            // TODO move catch bloc to extension of flow
        }
}