package controller

import database.ConexionPersonaje
import io.ktor.http.*
import model.*

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

    fun checkPersonajeVivo(idPartida: Int, idUsuario: Int): Boolean {
        val personajes = ConexionPersonaje.getPersonajeUsuarioPartida(idUsuario, idPartida)
        var vivos = 0

        for (personaje in personajes) {
            val estado = ConexionPersonaje.getCapacidadActual(personaje, idPartida)
            if (estado > 0) {
                vivos++
            }
        }

        return vivos > 0
    }


}