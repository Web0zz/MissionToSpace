package com.web0zz.network

import com.web0zz.network.model.LaunchesDto
import com.web0zz.network.util.Constants.LATEST_VERSION
import com.web0zz.network.util.Constants.LAUNCHES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceXApi {
    @GET(LATEST_VERSION + LAUNCHES)
    suspend fun getLaunches(): Response<List<LaunchesDto>>

    @GET("$LATEST_VERSION$LAUNCHES/{id}")
    suspend fun getLaunchesById(@Path("id") launchesId: String): Response<LaunchesDto>
}