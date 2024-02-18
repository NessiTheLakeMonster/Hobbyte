package routes

import controller.PartidaController
import controller.PersonajeController
import controller.UsuarioController
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Partida
import model.Personaje
import model.RespuestaConTablero
import utils.TokenManager

//private val personajes = arrayListOf<Personaje>(
//    Personaje("Gandalf", 1, 50),
//    Personaje("Thorin", 2, 50),
//    Personaje("Bilbo", 3, 50),
//)

fun Route.partidaRouting() {
    val tokenManager = TokenManager()

    route("/generarPartida/{idUsuario}") {
        authenticate {
            post {
                val principal = call.principal<JWTPrincipal>()
                var emailSolicitante = principal!!.payload.getClaim("email").asString()

                println(emailSolicitante)
                emailSolicitante = emailSolicitante.replace("\"", "")
                var user = UsuarioController.getUsuario(emailSolicitante)

                if (user != null) {

                    println("Usuario encontrado")

                    val partida = call.parameters["idUsuario"] ?: return@post call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val par = partida.toInt()

                    val respuesta = PartidaController.addPartida(Partida(par, "En curso", 20, 0))
                    call.respond(respuesta)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }

            }
        }

    }

    route("/generarPartidaCustom/{idUsuario}/{casillas_totales}") {
        authenticate {
            post {
                val principal = call.principal<JWTPrincipal>()
                var emailSolicitante = principal!!.payload.getClaim("email").asString()

                println(emailSolicitante)
                emailSolicitante = emailSolicitante.replace("\"", "")
                var user = UsuarioController.getUsuario(emailSolicitante)

                if (user != null) {

                    println("Usuario encontrado")

                    val partida = call.parameters["idUsuario"] ?: return@post call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val casillas = call.parameters["casillas_totales"] ?: return@post call.respondText(
                        "casillas vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val par = partida.toInt()
                    val cas = casillas.toInt()

                    PartidaController.generarPartidaCustom(par, cas)
                    call.respondText("Partida creada", status = HttpStatusCode.Created)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }

            }
        }

    }

    route("/verTablero/{idPartida}") {
        authenticate {
            get {
                val principal = call.principal<JWTPrincipal>()
                var emailSolicitante = principal!!.payload.getClaim("email").asString()

                println(emailSolicitante)
                emailSolicitante = emailSolicitante.replace("\"", "")
                var user = UsuarioController.getUsuario(emailSolicitante)

                if (user != null) {

                    println("Usuario encontrado")

                    val partida = call.parameters["idPartida"] ?: return@get call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val par = partida.toInt()

                    val respuesta = PartidaController.verTablero(par)
                    call.respond(respuesta)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }

            }
        }

    }

    route("/verCasillasTotales/{idPartida}") {
        authenticate {
            get {
                val principal = call.principal<JWTPrincipal>()
                var emailSolicitante = principal!!.payload.getClaim("email").asString()

                println(emailSolicitante)
                emailSolicitante = emailSolicitante.replace("\"", "")
                var user = UsuarioController.getUsuario(emailSolicitante)

                if (user != null) {

                    println("Usuario encontrado")

                    val partida = call.parameters["idPartida"] ?: return@get call.respondText(
                        "id vacío en la url",
                        status = HttpStatusCode.BadRequest
                    )

                    val par = partida.toInt()

                    val respuesta = PartidaController.verCasillasTotales(par)
                    call.respondText("Casillas totales: ${respuesta}", status = HttpStatusCode.OK)

                } else {

                    println("Usuario no encontrado")
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }

            }
        }

    }
}