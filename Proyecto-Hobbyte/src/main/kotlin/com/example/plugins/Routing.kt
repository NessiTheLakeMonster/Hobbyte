package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Respuesta
import routes.partidaRouting
import routes.pruebasRouting
import routes.userRouting

fun Application.configureRouting() {
    routing {
        get("/") {
            call.response.status(HttpStatusCode.OK)
            call.respond(Respuesta("Servidor funcionando", HttpStatusCode.OK.value))
        }
        userRouting()
        pruebasRouting()
        partidaRouting()
    }
}
