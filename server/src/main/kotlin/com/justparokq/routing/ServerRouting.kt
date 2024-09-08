package com.justparokq.routing

import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.justparokq.ftp.mapper.FileResponseMapper
import com.justparokq.ftp.utils.FileSystemCommunicator
import com.justparokq.ftp.utils.PathConstants.PHOTO_TYPE_PNG
import com.justparokq.ftp.utils.PathProcessor
import com.justparokq.ftp.utils.PhotoProcessor
import com.justparokq.homeftp.models.login.LoginRequest
import com.justparokq.homeftp.models.login.LoginResponse
import com.justparokq.login.data.LoginRequestMapper
import com.justparokq.login.data.UserRepositoryImpl
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.principal
import io.ktor.server.http.content.resource
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Date

private const val EXPIRATION_TIME = 10_000

fun Application.serverRouting(jwkProvider: JwkProvider) {
    install(ContentNegotiation) {
        json()
    }
    // reading from application.conf
    val privateKeyString = environment.config.property("jwt.privateKey").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    val userRepository = UserRepositoryImpl()
    val loginRequestMapper = LoginRequestMapper()

    installJWTAuthentication(myRealm, jwkProvider, issuer)
    routing {
        addUnauthorizedRoutes(
            jwkProvider,
            privateKeyString,
            audience,
            issuer,
            userRepository,
            loginRequestMapper
        )

        authenticate("auth-jwt") {
            addJWTAuthorizedRoutes()
        }
        route(".well-known") {
            resource("jwks.json")
        }
    }
}

private fun Application.installJWTAuthentication(
    myRealm: String,
    jwkProvider: JwkProvider,
    issuer: String,
) {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(jwkProvider, issuer) {
                acceptLeeway(3)
            }
            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}

private fun Routing.addUnauthorizedRoutes(
    jwkProvider: JwkProvider,
    privateKeyString: String,
    audience: String,
    issuer: String,
    userRepository: UserRepositoryImpl,
    loginRequestMapper: LoginRequestMapper,
) {
    post("/login") {
        val user = call.receive<LoginRequest>()

        val userModel = loginRequestMapper.map(user)
        // Check username and password
        val isValidUser = userRepository.doesUserExist(userModel)

        if (isValidUser) {
            val publicKey = jwkProvider.get("6f8856ed-9189-488f-9011-0ff4b6c08edc").publicKey
            val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
            val privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8)
            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.RSA256(publicKey as RSAPublicKey, privateKey as RSAPrivateKey))

            call.respond(
                LoginResponse(token = token)
            )
        } else {
            call.respond(HttpStatusCode.Unauthorized)
        }
    }

    // todo move to auth zone + another file
    val pathProcessor = PathProcessor("./server/testStorageDirectory/")
    val communicator = FileSystemCommunicator(pathProcessor)
    val mapper = FileResponseMapper(pathProcessor)
    val photoProcessor = PhotoProcessor(pathProcessor)
    get("/image") {
        val path = call.parameters["path"]
        val preview = call.parameters["preview"].toBoolean()

        when {
            path.isNullOrEmpty() -> call.respond(HttpStatusCode.BadRequest, "Empty path param")
            else -> {
                val file = communicator.getFile(path)

                if (file == null || file.extension != PHOTO_TYPE_PNG) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        "File not found or file extension is not supported"
                    )
                } else {
                    // todo rework
                    val fileToSend = if (preview) {
                        photoProcessor.getPreviewForPhoto(file)
                    } else file
                    call.respondFile(fileToSend)
                }
            }
        }
    }
    get("/directory") {
        val path = call.parameters["path"] ?: ""
        val files = communicator.getDirectoryContent(path) ?: listOf()
        val dtos = mapper.map(files)

        call.respond(dtos)
    }
}

private fun Route.addJWTAuthorizedRoutes() {
    get("/hello") {
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()
        call.respondText("Hello, $username!")
    }
}