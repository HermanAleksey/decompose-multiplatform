package com.justparokq.homeftp.shared.login.network

import com.justparokq.homefpt.shared.core.network.url.UrlResolver
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.core.setting_store.NetworkStore
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.LoginResponse
import com.justpoarokq.shared.core.base_database.api.NetworkSettingsInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface LoginRepository {
    suspend fun sendLoginRequest(loginRequest: LoginRequest): Flow<Result<LoginResponse>>
}

internal class LoginRepositoryImpl(
    private val networkSettingsInteractor: NetworkSettingsInteractor,
    private val loginNetworkComponent: LoginNetworkComponent,
    private val urlResolver: UrlResolver,
) : LoginRepository {

    override suspend fun sendLoginRequest(loginRequest: LoginRequest): Flow<Result<LoginResponse>> {
        val option = networkSettingsInteractor.getTargetOption()
        return when (option) {
            Setting.NetworkKey.Target.Option.Mock -> {
                flow {
                    emit(Result.Loading(true))
                    delay(500)
                    emit(
                        Result.Success(
                            LoginResponse(
                                token = "fake_token",
                                refreshToken = "fake_refresh"
                            )
                        )
                    )
                    emit(Result.Loading(false))
                }
            }

            Setting.NetworkKey.Target.Option.Dev -> {
                val baseUrl = urlResolver.getBaseUrl(isDevEnv = true)
                loginNetworkComponent.sendLoginRequest(loginRequest, baseUrl)
            }

            Setting.NetworkKey.Target.Option.Prod -> {
                val baseUrl = urlResolver.getBaseUrl(isDevEnv = false)
                loginNetworkComponent.sendLoginRequest(loginRequest, baseUrl)
            }
        }
    }
}
