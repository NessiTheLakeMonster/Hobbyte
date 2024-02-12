package routes

import controller.PersonajeController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Personaje

fun Route.personajeRouting() {

    route("/crearPersonajes/{idUsuario}") {
        post {
            val personaje = call.parameters["idUsuario"] ?: return@post call.respondText(
                "id vacío en la url",
                status = HttpStatusCode.BadRequest
            )

            val per = personaje.toInt()

            PersonajeController.addPersonaje(Personaje("Gandalf", 1, 50, per), per)
            PersonajeController.addPersonaje(Personaje("Thorin", 2, 50, per), per)
            PersonajeController.addPersonaje(Personaje("Bilbo", 3, 50, per), per)
            call.respondText("Personajes creados ${per}", status = HttpStatusCode.Created)
        }
    }

}