package com.web0zz.domain.usecase

import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetLaunchesUseCase(
    private val launchesRepository: LaunchesRepository,
    mainDispatcher: CoroutineDispatcher
) : UseCase<List<Launches>, Failure, UseCase.None>(mainDispatcher) {
    override suspend fun run(params: None) = launchesRepository.getLaunchesData()
}