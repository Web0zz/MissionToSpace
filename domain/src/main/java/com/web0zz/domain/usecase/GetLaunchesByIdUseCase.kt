package com.web0zz.domain.usecase

import com.github.michaelbull.result.Result
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository
import kotlinx.coroutines.flow.Flow

class GetLaunchesByIdUseCase(
    private val launchesRepository: LaunchesRepository
) : UseCase<Launches, Failure, String>() {
    override suspend fun run(params: String): Flow<Result<Launches, Failure>> =
        launchesRepository.getLaunchesById(params)
}