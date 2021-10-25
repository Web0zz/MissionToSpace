package com.web0zz.domain.usecase

import com.github.michaelbull.result.Result
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository
import kotlinx.coroutines.flow.Flow

class GetLunchesUseCase(
    private val launchesRepository: LaunchesRepository
) : UseCase<List<Launches>, Failure, UseCase.None>() {
    override suspend fun run(params: None): Flow<Result<List<Launches>, Failure>> =
        launchesRepository.getLaunchesData()
}