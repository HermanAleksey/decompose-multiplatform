package com.justparokq.login.data

import com.justparokq.login.model.UserModel
import com.example.myapplication.shared.login.models.login.LoginRequest

internal class LoginRequestMapper {

    internal fun map(request: LoginRequest): UserModel {
        return UserModel(
            username = request.username,
            password = request.password,
        )
    }
}