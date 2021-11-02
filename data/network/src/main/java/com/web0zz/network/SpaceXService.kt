package com.web0zz.network

import com.web0zz.network.util.getResponse
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpaceXService @Inject constructor(retrofit: Retrofit) {
    private val spaceXApi by lazy { retrofit.create(SpaceXApi::class.java) }

    suspend fun getLaunches() = getResponse { spaceXApi.getLaunches() }
    suspend fun getLaunchesById(launchesId: String) = getResponse {
        spaceXApi.getLaunchesById(launchesId)
    }
}