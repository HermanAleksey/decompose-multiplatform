package com.justparokq.homeftp.shared.login.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.componentCoroutineScope
import com.justparokq.homeftp.shared.login.data.network.LoginNetworkComponent
import com.justparokq.homeftp.shared.login.model.LoginScreenModel
import com.justparokq.homeftp.models.Result
import com.justparokq.homeftp.models.login.LoginRequest
import kotlinx.coroutines.launch

internal class DefaultLoginComponent(
    private val componentContext: ComponentContext,
    private val loginNetworkComponent: LoginNetworkComponent = LoginNetworkComponent(),
    private val navigateToMainPage: () -> Unit,
) : LoginComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val _state = MutableValue(LoginScreenModel())
    override val state: Value<LoginScreenModel> = _state

    override fun onUsernameFieldUpdated(newValue: String) {
        _state.update { it.copy(usernameTextField = newValue) }
    }

    override fun onPasswordFieldUpdated(newValue: String) {
        _state.update { it.copy(passwordTextField = newValue) }
    }

    override fun onLoginButtonClick() {
        coroutineScope.launch {
            val request = LoginRequest(
                username = _state.value.usernameTextField,
                password = _state.value.passwordTextField,
            )
            loginNetworkComponent.sendLoginRequest(request)
                .collect { response ->
                    when (response) {
                        is Result.Error -> {
                            // show error
                            println("onLoginButtonClick: error $response")
                        }

                        is Result.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = response.loading
                                )
                            }
                        }

                        is Result.Success -> {
                            // navigate to next screen
                            navigateToMainPage()
                        }
                    }
                }
        }
    }
}
