package controller

import database.ConexionCasilla
import database.ConexionPartida
import database.ConexionPersonaje
import database.ConexionPrueba
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

    fun destaparCasilla(idPartida: Int, idCasilla: Int): Respuesta {
        var cod = 0
        var msg = ""

        val idPrueba = ConexionCasilla.verTipoPrueba(idCasilla, idPartida)
        val casillasTotales = ConexionPartida.getCasillasTotales(idPartida)
        ConexionPartida.actualizarCasillasDestapadas(idPartida, casillasTotales)
        ConexionPartida.actualizarEstadoPartida(idPartida, "En juego")

        if (idPrueba == 1) {
            msg = "Prueba de magia"
            cod = 200
        } else if (idPrueba == 2) {
            msg = "Prueba de fuerza"
            cod = 200
        } else if (idPrueba == 3) {
            msg = "Prueba de habilidad"
            cod = 200
        } else {
            msg = "Error al buscar la prueba"
            cod = 400
        }

        return Respuesta(msg, cod)
    }

    fun realizarPrueba(idPartida: Int, idCasilla: Int, idPersonaje: Int): Respuesta {
        var cod = 0
        var msg = ""

        val capacidadActual = ConexionPersonaje.getCapacidadActual(idPartida, idPersonaje)
        val idPrueba = ConexionCasilla.verTipoPrueba(idCasilla, idPartida)
        val esfuerzo = ConexionPrueba.getEsfuerzo(idPrueba)
        var actualizado = 0

        if (capacidadActual > esfuerzo) {
            val capacidadNueva = capacidadActual * 0.9
            actualizado = ConexionPersonaje.actualizarCapacidadActual(idPartida, idPersonaje, capacidadNueva.toInt())
        } else if (capacidadActual == esfuerzo) {
            val capacidadNueva = capacidadActual * 0.7
            actualizado = ConexionPersonaje.actualizarCapacidadActual(idPartida, idPersonaje, capacidadNueva.toInt())
        } else if (capacidadActual < esfuerzo) {
            val capacidadNueva = capacidadActual * 0.5
            actualizado = ConexionPersonaje.actualizarCapacidadActual(idPartida, idPersonaje, capacidadNueva.toInt())
        }

        if (actualizado != 0) {
            msg = "Prueba realizada correctamente"
            cod = 200
        } else {
            msg = "Error al realizar la prueba"
            cod = 400
        }

        return Respuesta(msg, cod)
    }

    fun ganarPartida(idPartida: Int): Boolean {
        val casillasDestapadas = ConexionPartida.getCasillasDestapadas(idPartida)
        val casillasTotales = ConexionPartida.getCasillasTotales(idPartida)
        var ganar = false

        if (casillasTotales / 2 == casillasDestapadas) {
            ganar = true
            ConexionPartida.actualizarEstadoPartida(idPartida, "Ganada")
        } else {
            ganar = false
        }

        return ganar
    }

}