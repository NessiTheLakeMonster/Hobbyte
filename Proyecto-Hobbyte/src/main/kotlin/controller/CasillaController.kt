package controller

import database.ConexionCasilla
import model.Casilla
import model.Respuesta

object CasillaController {

    fun crearCasilla(casilla: Casilla): Respuesta {
        var cod = 0
        var msg = ""

        val insertado = ConexionCasilla.insertarCasilla(
            casilla.idPartida,
            casilla.idTipoPrueba,
            casilla.estadoPrueba
        )

        if (insertado != 0) {
            msg = "Casilla insertada correctamente"
            cod = 201
        } else {
            msg = "Error al insertar la casilla"
            cod = 400
        }

        return Respuesta(msg, cod)
    }
}