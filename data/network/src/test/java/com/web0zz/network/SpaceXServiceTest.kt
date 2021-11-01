package com.web0zz.network

import com.google.common.truth.Truth.assertThat
import com.web0zz.network.exception.ApiException
import com.web0zz.network.util.MockWebServerUtil.enqueueErrorResponse
import com.web0zz.network.util.MockWebServerUtil.enqueueResponse
import com.web0zz.network.util.expectedLaunchesDto
import com.web0zz.network.util.getResponse
import com.web0zz.network.util.notFoundMessage
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
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
    fun `should fetch Launches correctly given 200 response`() {
        mockWebServer.enqueueResponse("launches-200.json", 200)

        runBlocking {
            val actual = service.getLaunches().getResponse()
            val expected = expectedLaunchesDto

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `should fetch Launches by Id correctly given 200 response`() {
        mockWebServer.enqueueResponse("launches-by-id-200.json", 200)

        runBlocking {
            val actual = service.getLaunchesById("5eb87cd9ffd86e000604b32a").getResponse()
            val expected = expectedLaunchesDto.first()

            assertThat(actual).isEqualTo(expected)
        }
    }

    /*@Test
    fun `should fetch errorBody given 404 response`() {
        mockWebServer.enqueueErrorResponse(notFoundMessage, 404)
        var errorBody = ""

        runBlocking {
            try {
                val actual = service.getLaunchesById("NotExistId").getResponse()
            } catch (e: ApiException) {
                e.message?.let { errorBody = it }
            }

            assertThat(errorBody).isEqualTo(notFoundMessage)
        }
    }*/
}