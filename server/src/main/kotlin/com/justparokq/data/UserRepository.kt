package com.justparokq.data

import com.justparokq.model.UserModel

interface UserRepository {

    fun save(user: UserModel): Boolean

    fun findByUsername(username: String): UserModel

    fun doesUserExist(user: UserModel): Boolean
}