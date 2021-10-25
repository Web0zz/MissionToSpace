package com.web0zz.network

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpaceXService
@Inject constructor(retrofit: Retrofit) : SpaceXApi {
    private val spaceXApi by lazy { retrofit.create(SpaceXApi::class.java) }

    override suspend fun getLaunches() = spaceXApi.getLaunches()
    override suspend fun getLaunchesById(launchesId: String) = spaceXApi.getLaunchesById(launchesId)
}