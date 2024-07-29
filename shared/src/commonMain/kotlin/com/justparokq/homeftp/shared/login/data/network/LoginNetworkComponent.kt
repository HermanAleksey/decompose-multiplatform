package com.justparokq.homeftp.shared.login.data.network

import com.justparokq.models.Result
import com.justparokq.models.login.LoginRequest
import com.justparokq.models.login.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

internal class LoginNetworkComponent {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    fun sendLoginRequest(
        loginRequest: LoginRequest,
    ): Flow<Result<LoginResponse>> {
        return flow {
            emit(Result.Loading(true))
            // todo remove
            delay(1000)
            try {
                val result = httpClient.request("http://10.0.2.2:8080/login") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(loginRequest)
                }
                emit(Result.Loading(false))
                if (result.status == HttpStatusCode.OK) {
                    val resultBody = result.body<LoginResponse>()
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