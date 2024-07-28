package com.example.myapplication.shared.login.presentation

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.login.model.LoginScreenModel

object PreviewLoginComponent : LoginComponent {
    override val state: Value<LoginScreenModel> = MutableValue(LoginScreenModel())

    override fun onUsernameFieldUpdated(newValue: String) = Unit

    override fun onPasswordFieldUpdated(newValue: String) = Unit

    override fun onLoginButtonClick() = Unit
}
