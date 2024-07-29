package com.justparokq.homeftp.shared.login.presentation

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.login.model.LoginScreenModel

interface LoginComponent {

    val state: Value<LoginScreenModel>

    fun onUsernameFieldUpdated(newValue: String)
    fun onPasswordFieldUpdated(newValue: String)
    fun onLoginButtonClick()
}
