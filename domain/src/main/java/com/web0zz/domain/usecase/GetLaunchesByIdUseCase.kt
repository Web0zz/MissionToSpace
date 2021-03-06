package com.web0zz.domain.usecase

import com.github.michaelbull.result.Result
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.launches.Launches
import com.web0zz.domain.repository.LaunchesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetLaunchesByIdUseCase(
    private val launchesRepository: LaunchesRepository,
    mainDispatcher: CoroutineDispatcher
) : UseCase<Launches, Failure, String>(mainDispatcher) {
    override suspend fun run(params: String): Flow<Result<Launches, Failure>> =
        launchesRepository.getLaunchesById(params)
}