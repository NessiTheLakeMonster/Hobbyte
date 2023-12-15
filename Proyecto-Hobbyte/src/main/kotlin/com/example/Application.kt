package com.example

import com.example.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import utils.Parametros
import utils.TokenManager

fun main() {
    embeddedServer(Netty, port = Parametros.port, host = Parametros.ip, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val tokenManager = TokenManager()

    install(Authentication) {
        jwt {
            verifier(tokenManager.verifyJWTToken())
            realm = Parametros.realm
            validate {
                if (it.payload.getClaim("email").asString().isNotEmpty()) {
                    JWTPrincipal(it.payload)
                } else {
                    null
                }
            }
        }
    }

    configureSerialization()
    configureRouting()
}
