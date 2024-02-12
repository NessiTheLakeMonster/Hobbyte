package routes

import controller.CasillaController
import controller.PartidaController
import database.ConexionCasilla
import database.ConexionPartida
import io.ktor.http.*
import io.ktor.server.application.*
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


    route("/destaparCasilla/{idPartida}") {
        get {
            val partida = call.parameters["idPartida"] ?: return@get call.respondText(
                "id vacío en la url",
                status = HttpStatusCode.BadRequest
            )

            val par = partida.toInt()

            val respuesta = CasillaController.destaparCasilla(par)

            call.respond(respuesta)
        }
    }

    route("/generarCasillas/{idPartida}") {
        post {
            val partida = call.parameters["idPartida"] ?: return@post call.respondText(
                "id vacío en la url",
                status = HttpStatusCode.BadRequest
            )

            val par = partida.toInt()

            val respuesta = PartidaController.insertarCasillas(par)
            call.respond(respuesta)
        }
    }
}