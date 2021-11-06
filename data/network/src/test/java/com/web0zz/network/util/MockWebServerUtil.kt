package com.web0zz.network.util

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

object MockWebServerUtil {

    internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

    internal fun MockWebServer.enqueueErrorResponse(bodyText: String, code: Int) {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(bodyText)
        )
    }
}