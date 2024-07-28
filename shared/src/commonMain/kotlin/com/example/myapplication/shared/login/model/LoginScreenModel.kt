package com.example.myapplication.shared.login.model

data class LoginScreenModel(
    val usernameTextField: String = "user",
    val passwordTextField: String = "pass",
    val isLoading: Boolean = false,
)