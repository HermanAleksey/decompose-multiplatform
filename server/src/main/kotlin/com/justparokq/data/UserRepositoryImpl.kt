package com.justparokq.data

import com.justparokq.model.UserModel

class UserRepositoryImpl : UserRepository {

    private val tempStorage = hashMapOf<String, UserModel>(
        "user" to UserModel("user", "pass")
    )

    override fun save(user: UserModel): Boolean {
        tempStorage[user.username] = user
        return true
    }

    override fun findByUsername(username: String): UserModel {
        return tempStorage[username] ?: throw NoSuchElementException("no such a user")
    }

    override fun doesUserExist(user: UserModel): Boolean {
        return tempStorage[user.username] == user
    }
}