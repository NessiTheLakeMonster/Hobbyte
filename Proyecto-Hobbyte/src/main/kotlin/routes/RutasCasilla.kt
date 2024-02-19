package routes

import controller.CasillaController
import controller.PartidaController
import controller.UsuarioController
import database.ConexionCasilla
import database.ConexionPartida
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Partida

fun Route.casillaRouting() {
    route("/crearCasilla/{idPartida}") {
        post {
            val partida = call.parameters["idPartida"] ?: return@post call.respondText(
                "id vacío en la url",
                status = HttpStatusCode.BadRequest
            )

            val par = partida.toInt()

            CasillaController.crearCasilla(par)
            call.respondText("Casilla creada", status = HttpStatusCode.Created)
        }
    }


    route("/destaparCasilla/{idCasilla}/{idPartida}") {
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

                    val casilla = call.parameters["idCasilla"] ?: return@post call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val par = partida.toInt()
                    val cas = casilla.toInt()

                    val respuesta = CasillaController.destaparCasilla(par, cas)

                    call.respond(respuesta)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }
            }
        }
    }

    route("/generarCasillas/{idPartida}") {
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

                    val par = partida.toInt()

                    val respuesta = CasillaController.generateCasillas(par)
                    call.respond(respuesta)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }

            }
        }
    }

    route("/realizarPrueba/{idPartida}/{idCasilla}/{idPersonaje}") {
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

                    val casilla = call.parameters["idCasilla"] ?: return@post call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val personaje = call.parameters["idPersonaje"] ?: return@post call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val par = partida.toInt()
                    val cas = casilla.toInt()
                    val per = personaje.toInt()

                    val respuesta = CasillaController.realizarPrueba(par, cas, per)
                    call.respond(respuesta)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }

            }
        }
    }
}