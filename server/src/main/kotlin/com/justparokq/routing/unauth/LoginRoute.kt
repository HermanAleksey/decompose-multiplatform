package com.justparokq.routing.unauth

import Dependencies
import com.auth0.jwk.JwkProvider
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.LoginResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

// 1 hour
internal const val TEST_TOKEN_EXPIRATION_TIME = 1 * 60 * 60 * 100L

// 30 minutes
internal const val PROD_TOKEN_EXPIRATION_TIME = 2 * 60 * 100L

internal const val RSA_ALGORITHM = "RSA"

internal const val REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L // 7 days

internal suspend fun RoutingContext.addLoginRoute(
    jwkProvider: JwkProvider,
    privateKeyString: String,
    audience: String,
    issuer: String,
    isTest: Boolean
) {
    val tokenService = Dependencies.getTokenService()
    val userRepository = Dependencies.getUserRepository(isTest = isTest)
    val loginRequestMapper = Dependencies.getLoginRequestMapper()
    val tokenExpirationTime = Dependencies.getTokenExpirationTime(isTest = isTest)
    val refreshTokenExpirationTime = Dependencies.getRefreshTokenExpirationTime(isTest = isTest)

    val user = call.receive<LoginRequest>()

    val userModel = loginRequestMapper.map(user)
    val isValidUser = userRepository.doesUserExist(userModel)

    if (isValidUser) {
        val (publicKey, privateKey) = tokenService.extractKeys(jwkProvider, privateKeyString)
        val token = tokenService.generateAccessToken(
            username = user.username,
            audience = audience,
            issuer = issuer,
            expiresInMillis = tokenExpirationTime,
            publicKey = publicKey,
            privateKey = privateKey
        )
        val refreshToken = tokenService.generateRefreshToken(
            username = user.username,
            expiresInMillis = refreshTokenExpirationTime,
            isTest = false
        )
        call.respond(LoginResponse(token = token, refreshToken = refreshToken))
    } else {
        call.respond(HttpStatusCode.Unauthorized)
    }
}  