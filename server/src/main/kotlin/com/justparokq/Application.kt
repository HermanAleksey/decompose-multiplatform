package com.justparokq

import com.auth0.jwk.JwkProviderBuilder
import com.justparokq.routing.serverRouting
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    EngineMain.main(args)
}

// This method is called via reflection by the fully qualified name of the module in the application.conf file
@Suppress("UNUSED")
fun Application.main() {
    val issuer = environment.config.property("jwt.issuer").getString()
    val jwkProvider = JwkProviderBuilder(issuer)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 10, TimeUnit.MINUTES)
        .build()

    serverRouting(jwkProvider)
}
