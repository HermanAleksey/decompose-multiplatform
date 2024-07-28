package com.example.myapplication.shared.login.presentation

import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.login.model.LoginScreenModel

interface LoginComponent {

    val state: Value<LoginScreenModel>

    fun onUsernameFieldUpdated(newValue: String)
    fun onPasswordFieldUpdated(newValue: String)
    fun onLoginButtonClick()
}
