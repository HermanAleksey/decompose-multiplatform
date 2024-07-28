package com.justparokq.data

import com.justparokq.model.UserModel
import com.example.myapplication.shared.login.models.login.LoginRequest

class LoginRequestMapper {

    fun map(request: LoginRequest): UserModel {
        return UserModel(
            username = request.username,
            password = request.password,
        )
    }
}