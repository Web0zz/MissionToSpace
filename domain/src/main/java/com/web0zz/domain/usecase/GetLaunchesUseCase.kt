package com.web0zz.domain.usecase

import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository

class GetLaunchesUseCase(
    private val launchesRepository: LaunchesRepository
) : UseCase<List<Launches>, Failure, UseCase.None>() {
    override suspend fun run(params: None) = launchesRepository.getLaunchesData()
}