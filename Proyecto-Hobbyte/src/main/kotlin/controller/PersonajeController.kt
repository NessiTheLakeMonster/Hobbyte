package controller

import database.ConexionPersonaje
import io.ktor.http.*
import model.EstadoPersonaje
import model.Personaje
import model.Respuesta
import model.Usuario

object PersonajeController {

    fun addPersonaje(personaje: Personaje, idUsuario: Int): Respuesta {
        var cod = 0

        var insertado = ConexionPersonaje.insertarPersonaje(
            personaje.nombre,
            personaje.idTipoPrueba,
            personaje.capacidadMax,
            idUsuario
        )

        var msg = ""

        if (insertado != 0) {
            msg = "Personaje insertado correctamente"
            cod = HttpStatusCode.Created.value
        } else {
            msg = "Error al insertar el personaje"
            cod = HttpStatusCode.BadRequest.value
        }

        return Respuesta(msg, cod)
    }

    fun generarEstadoPj(estadoPersonaje: EstadoPersonaje): Respuesta {
        var cod = 0

        var insertado = ConexionPersonaje.generarEstadoPj(
            estadoPersonaje.idUsuario,
            estadoPersonaje.idPartida,
            estadoPersonaje.idPersonaje,
            estadoPersonaje.capacidadActual
        )

        var msg = ""

        if (insertado != 0) {
            msg = "Estado del personaje insertado correctamente"
            cod = HttpStatusCode.Created.value
        } else {
            msg = "Error al insertar el estado del personaje"
            cod = HttpStatusCode.BadRequest.value
        }

        return Respuesta(msg, cod)
    }
}