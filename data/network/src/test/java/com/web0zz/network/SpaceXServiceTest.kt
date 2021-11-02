package com.web0zz.network

import com.google.common.truth.Truth.assertThat
import com.web0zz.network.util.MockWebServerUtil.enqueueErrorResponse
import com.web0zz.network.util.MockWebServerUtil.enqueueResponse
import com.web0zz.network.util.NetworkResponse
import com.web0zz.network.util.expectedLaunchesDto
import com.web0zz.network.util.notFoundMessage
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SpaceXServiceTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val service = SpaceXService(api)

    @After
    fun closeServer() {
        mockWebServer.close()
    }

    @Test
    fun `should get Success type and fetch Launches correctly with a 200 response`() {
        mockWebServer.enqueueResponse("launches-200.json", 200)

        runBlocking {
            val request = service.getLaunches() as NetworkResponse.Success

            val actualData = request.data
            val expectedData = expectedLaunchesDto

            assertThat(actualData).isEqualTo(expectedData)
        }
    }

    @Test
    fun `should get Success type and fetch Launches by Id correctly with a 200 response`() {
        mockWebServer.enqueueResponse("launches-by-id-200.json", 200)

        runBlocking {
            val request = service
                .getLaunchesById("5eb87cd9ffd86e000604b32a") as NetworkResponse.Success

            val actualData = request.data
            val expectedData = expectedLaunchesDto.first()

            assertThat(actualData).isEqualTo(expectedData)
        }
    }

    @Test
    fun `should get Error type and fetch errorBody with a 404 response`() {
        mockWebServer.enqueueErrorResponse(notFoundMessage, 404)

        runBlocking {
            val request = service.getLaunchesById("NotExistId") as NetworkResponse.Error

            assertThat(request.message).isEqualTo(notFoundMessage)
        }
    }
}