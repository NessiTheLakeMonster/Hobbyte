package routes

import controller.PartidaController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Partida

fun Route.partidaRouting() {
    route("/crearPartida/{idUsuario}") {
        post {
            val partida = call.parameters["idUsuario"] ?: return@post call.respondText(
                "id vacío en la url",
                status = HttpStatusCode.BadRequest
            )

            val par = partida.toInt()
            println(par)

            PartidaController.addPartida(Partida(par, "En curso"))
            call.respondText("Partida creada", status = HttpStatusCode.Created)
        }
    }
}