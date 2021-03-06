package com.web0zz.repository.mapper

import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.domain.model.launches.Launches
import com.web0zz.network.model.LaunchesDto

class DataMappersFacade(
    val launchesDtoMapper: (LaunchesDto) -> Launches,
    val launchesEntityMapper: (LaunchesEntity) -> Launches,
    val launchesDtoToEntityMapper: (LaunchesDto) -> LaunchesEntity
)