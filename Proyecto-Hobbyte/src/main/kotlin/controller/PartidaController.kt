package controller

import database.ConexionPartida
import io.ktor.http.*
import model.Partida
import model.Respuesta

object PartidaController {

    fun addPartida(partida: Partida): Respuesta {
        var cod = 0

        var insertado = ConexionPartida.crearPartida(
            partida.idUsuario,
            partida.estado
        )

        var msg = ""

        if (insertado != 0) {
            msg = "Partida insertada correctamente"
            cod = HttpStatusCode.Created.value
        } else {
            msg = "Error al insertar la partida"
            cod = HttpStatusCode.BadRequest.value
        }

        return Respuesta(msg, cod)
    }

    fun generarPartida(idPartida: Int) : List<Int> {
        val ids = PruebaController.getIdPruebas()
        ids.shuffled()


        return ids
    }
}