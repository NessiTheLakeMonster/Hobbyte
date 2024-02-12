package controller

import database.ConexionCasilla
import database.ConexionPartida
import model.Casilla
import model.Respuesta

object CasillaController {

    fun crearCasilla(idPartida: Int): Respuesta {
        var cod = 0
        var msg = ""

        var casillas = generateCasillas(idPartida)

        for (casilla in casillas) {
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
        }

        return Respuesta(msg, cod)
    }

    fun generateCasillas(idPartida: Int): List<Casilla> {
        val casillas = ArrayList<Casilla>()

        for (i in 0..19) {
            val id = PruebaController.getIdPruebas()
            id.shuffled()
            val casilla = Casilla(idPartida, id[i], "Prueba no comenzada")
            casillas.add(casilla)
        }

        return casillas
    }

    fun destaparCasilla(idPartida:Int): Respuesta {
        var cod = 0
        var msg = ""

        val casillas = ConexionCasilla.insertarCasilla(idPartida, PruebaController.getIdPruebas().random(), "Prueba en curso")

        // Se actualiza el número de casillas destapadas
        val casillasDestapadas = ConexionPartida.getCasillasDestapadas(idPartida)
        ConexionPartida.actualizarCasillasDestapadas(idPartida, casillasDestapadas + 1)

        if (casillas != null) {
            msg = "Casillas obtenidas correctamente ${casillas.toString()}"
            cod = 200
        } else {
            msg = "Error al obtener las casillas"
            cod = 400
        }

        return Respuesta(msg, cod)
    }

}