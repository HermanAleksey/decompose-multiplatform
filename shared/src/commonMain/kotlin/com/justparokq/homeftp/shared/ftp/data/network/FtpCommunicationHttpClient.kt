package com.justparokq.homeftp.shared.ftp.data.network

import com.justparokq.homeftp.models.Result
import com.justparokq.homeftp.models.ftp.FileResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

val file = FileResponse(
    uri = "src/lesha/hello",
    name = "text.txt",
    isDirectory = false,
)

interface FtpCommunicationHttpClient {

    fun getDirectoryContent(directoryUri: String): Flow<Result<List<FileResponse>>>
}

internal class FtpCommunicationHttpClientImpl : FtpCommunicationHttpClient {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    // request to http://0.0.0.0:8080/directory?path=directoryUri
    override fun getDirectoryContent(
        directoryUri: String,
    ): Flow<Result<List<FileResponse>>> {
        return flow {
            emit(Result.Loading(true))
            // todo remove
            delay(1000)
            try {
                val url = URLBuilder(
                    host = "0.0.0.0",
                    port = 8080,
                    pathSegments = listOf("directory"),
                ).build()

                val result = httpClient.request(url) {
                    if(directoryUri.isNotEmpty()) {
                        parameter("path", directoryUri)
                    }
                    method = HttpMethod.Get
                    contentType(ContentType.Application.Json)
                }

                emit(Result.Loading(false))

                if (result.status == HttpStatusCode.OK) {
                    val resultBody = result.body<List<FileResponse>>()
                    emit(Result.Success(resultBody))
                } else {
                    emit(Result.Error(errorMessage = "failed with error: ${result.status.value}"))
                }
            } catch (e: Exception) {
                emit(Result.Loading(false))
                emit(Result.Error(errorMessage = e.message ?: "Unknown error"))
            }
        }
    }
}