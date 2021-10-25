package com.web0zz.repository.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.web0zz.cache.dao.LaunchesDao
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository
import com.web0zz.network.SpaceXService
import com.web0zz.network.model.LaunchesDto
import com.web0zz.network.util.NetworkHandler
import com.web0zz.network.util.getResponse
import com.web0zz.repository.mapper.DataMappersFacade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaunchesRepositoryImp @Inject constructor(
    private val apiService: SpaceXService,
    private val launchesDao: LaunchesDao,
    private val dataMappersFacade: DataMappersFacade,
    private val networkHandler: NetworkHandler
) : LaunchesRepository {

    /**
     *  [getResponse] can throw exception in case unexpected response, that will cause to skip load from cache
     *  TODO fix that
     */
    override suspend fun getLaunchesData(): Flow<Result<List<Launches>, Failure>> = flow {
        try {
            lateinit var result : Result<List<Launches>, Failure>

            if(networkHandler.checkNetworkStat()) {
                val apiResponse : List<LaunchesDto> = apiService.getLaunches().getResponse()

                result = if (apiResponse.isNotEmpty()) {
                    val data = apiResponse.map { dataMappersFacade.launchesDtoMapper(it) }

                    launchesDao.insertLaunches(
                        apiResponse.map { dataMappersFacade.launchesDtoToEntityMapper(it) }
                    )
                    Ok(data)
                } else {
                    val data = launchesDao.getAllLaunches()
                        .map { dataMappersFacade.launchesEntityMapper(it) }

                    Ok(data)
                }
            } else {
                val data = launchesDao.getAllLaunches()
                    .map { dataMappersFacade.launchesEntityMapper(it) }

                result = Ok(data)
            }

            emit(result)
        } catch (e: Exception) {
            emit(Err(Failure.UnknownError("Error")))
        }
    }

    override suspend fun getLaunchesById(launchesId: String): Flow<Result<List<Launches>, Failure>> = flow {
        try {
            val cacheResponse : List<LaunchesEntity> = launchesDao.getLaunches(launchesId)

            val result : Result<List<Launches>, Failure> = if (cacheResponse.isNotEmpty()) {
                val data = cacheResponse.map { dataMappersFacade.launchesEntityMapper(it) }

                Ok(data)
            } else {
                if (networkHandler.checkNetworkStat()) {
                    val apiResponse = apiService.getLaunchesById(launchesId).getResponse()

                    if (apiResponse.isNotEmpty()) {
                        val data = apiResponse.map { dataMappersFacade.launchesDtoMapper(it) }

                        launchesDao.insertLaunches(
                            apiResponse.map { dataMappersFacade.launchesDtoToEntityMapper(it) }
                        )
                        Ok(data)
                    } else {
                        throw IOException("Error")
                    }
                } else throw IOException("Error")
            }

            emit(result)
        } catch (e: Exception) {
            emit(Err(Failure.UnknownError("Error")))
        }
    }
}