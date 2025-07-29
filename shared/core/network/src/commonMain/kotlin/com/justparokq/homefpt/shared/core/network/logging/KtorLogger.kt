package com.justparokq.homefpt.shared.core.network.logging

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

internal expect val isDebugBuild: Boolean

fun HttpClientConfig<*>.configureLogging() {
    println("[Ktor] Configuring ktor logging... isDebugBuild = $isDebugBuild")
    if (isDebugBuild) {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("[Ktor] $message") // Use platform-specific logging later
                }
            }
        }
    }
}