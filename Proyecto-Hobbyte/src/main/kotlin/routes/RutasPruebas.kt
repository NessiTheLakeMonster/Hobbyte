package routes

import controller.PruebaController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Prueba

fun Route.pruebasRouting() {
    route("/insertarPruebas") {
        post {
            PruebaController.addPruebas()
            call.respondText("Pruebas insertadas correctamente", status = HttpStatusCode.Created)
        }
    }
}