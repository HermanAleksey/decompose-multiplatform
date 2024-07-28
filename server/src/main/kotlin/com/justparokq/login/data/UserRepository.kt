package com.justparokq.login.data

import com.justparokq.login.model.UserModel

internal interface UserRepository {

    fun save(user: UserModel): Boolean

    fun findByUsername(username: String): UserModel

    fun doesUserExist(user: UserModel): Boolean
}