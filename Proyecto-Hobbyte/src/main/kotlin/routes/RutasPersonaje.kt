package routes

import controller.PersonajeController
import controller.UsuarioController
import database.ConexionCasilla
import database.ConexionPersonaje
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.EstadoPersonaje
import model.Personaje

fun Route.personajeRouting() {

    route("/crearPersonajes/{idUsuario}") {
        authenticate {
            post {
                val principal = call.principal<JWTPrincipal>()
                var emailSolicitante = principal!!.payload.getClaim("email").asString()

                println(emailSolicitante)
                emailSolicitante = emailSolicitante.replace("\"", "")
                var user = UsuarioController.getUsuario(emailSolicitante)

                if (user != null) {

                    println("Usuario encontrado")

                    val personaje = call.parameters["idUsuario"] ?: return@post call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val per = personaje.toInt()

                    PersonajeController.addPersonaje(Personaje("Gandalf", 1, 50, per), per)
                    PersonajeController.addPersonaje(Personaje("Thorin", 2, 50, per), per)
                    PersonajeController.addPersonaje(Personaje("Bilbo", 3, 50, per), per)
                    call.respondText("Personajes creados ${per}", status = HttpStatusCode.Created)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }
            }
        }
    }

    route("/generarEstadoPj/{idPartida}/{idUsuario}") {
        authenticate {
            post {
                val principal = call.principal<JWTPrincipal>()
                var emailSolicitante = principal!!.payload.getClaim("email").asString()

                println(emailSolicitante)
                emailSolicitante = emailSolicitante.replace("\"", "")
                var user = UsuarioController.getUsuario(emailSolicitante)

                if (user != null) {

                    println("Usuario encontrado")

                    val partida = call.parameters["idPartida"] ?: return@post call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val idUsuario = call.parameters["idUsuario"] ?: return@post call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val par = partida.toInt()
                    val us = idUsuario.toInt()

                    val ids = ConexionPersonaje.getPersonajeUsuarioPartida(us, par)

                    for (i in ids) {
                        PersonajeController.generarEstadoPj(EstadoPersonaje(par, i, 50))
                    }
                    call.respondText("Estado de personajes insertado", status = HttpStatusCode.Created)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }
            }
        }
    }

}