package com.justparokq.homeftp.shared.login.model

data class LoginScreenModel(
    val usernameTextField: String = "user",
    val passwordTextField: String = "pass",
    val isLoading: Boolean = false,
)