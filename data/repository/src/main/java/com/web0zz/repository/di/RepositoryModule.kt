package com.web0zz.repository.di

import com.web0zz.cache.dao.LaunchesDao
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository
import com.web0zz.network.SpaceXService
import com.web0zz.network.model.LaunchesDto
import com.web0zz.network.util.NetworkHandler
import com.web0zz.repository.mapper.DataMappersFacade
import com.web0zz.repository.mapper.mapLaunchesDto
import com.web0zz.repository.mapper.mapLaunchesDtoToEntity
import com.web0zz.repository.mapper.mapLaunchesEntity
import com.web0zz.repository.repository.LaunchesRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLaunchesRepository(
        spaceXService: SpaceXService,
        launchesDao: LaunchesDao,
        dataMappersFacade: DataMappersFacade,
        networkHandler: NetworkHandler
    ): LaunchesRepository = LaunchesRepositoryImp(
        spaceXService,
        launchesDao,
        dataMappersFacade,
        networkHandler
    )

    @Provides
    fun provideDataMappersFacade(): DataMappersFacade =
        DataMappersFacade(
            makeLaunchesDtoDataMapper(),
            makeLaunchesEntityDataMapper(),
            makeLaunchesDtoToEntityDataMapper()
        )

    private fun makeLaunchesDtoDataMapper(): (LaunchesDto) -> Launches =
        { launchesDto -> mapLaunchesDto(launchesDto) }

    private fun makeLaunchesEntityDataMapper(): (LaunchesEntity) -> Launches =
        { launchesEntity -> mapLaunchesEntity(launchesEntity) }

    private fun makeLaunchesDtoToEntityDataMapper(): (LaunchesDto) -> LaunchesEntity =
        { launchesDto -> mapLaunchesDtoToEntity(launchesDto) }
}