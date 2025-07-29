package com.justparokq.homefpt.shared.core.network.httpclient

import com.justparokq.homefpt.shared.core.network.logging.configureLogging
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun getUnauthHttpClient(): HttpClient {
    return HttpClient {
        configureLogging()
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}