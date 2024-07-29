package com.justparokq.models

sealed interface Result<out T> {

    data class Loading<T>(
        val loading: Boolean
    ) : Result<T>

    data class Success<T>(
        val result: T,
    ) : Result<T>

    data class Error<T>(
        val errorMessage: String,
    ) : Result<T>
}