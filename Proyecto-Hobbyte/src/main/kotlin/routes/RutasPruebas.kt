package routes

import controller.PruebaController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Prueba

fun Route.pruebasRouting() {
    route("/insertarPruebas/{cantidad}") {
        post {
            val cantidad = call.parameters["cantidad"] ?: return@post call.respondText(
                "Cantidad vacía en la url",
                status = HttpStatusCode.BadRequest
            )

            val cant = cantidad.toInt()

            PruebaController.addPruebas(cant)
            call.respondText("Pruebas insertadas correctamente", status = HttpStatusCode.Created)
        }
    }

    route("/insertar") {
        post {
            PruebaController.addPruebas(20)
            call.respondText("Pruebas 20 insertadas correctamente", status = HttpStatusCode.Created)
        }
    }

    route("/obtenerPruebasId") {
        get {
            val respuesta = PruebaController.getIdPruebas()
            println(respuesta.toString())

            call.respondText { respuesta.toString() }
        }
    }
}