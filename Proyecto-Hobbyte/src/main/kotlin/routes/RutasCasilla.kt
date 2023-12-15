package routes

import controller.PartidaController
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

            PartidaController.addPartida(Partida(par, "Prueba no comenzada"))
            call.respondText("Casilla creada", status = HttpStatusCode.Created)
        }
    }
}