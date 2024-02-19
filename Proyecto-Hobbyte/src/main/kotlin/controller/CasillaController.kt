package controller

import database.ConexionCasilla
import database.ConexionPartida
import database.ConexionPersonaje
import database.ConexionPrueba
import model.Casilla
import model.Respuesta
import kotlin.random.Random

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

        val idUsuario = ConexionPartida.getIdUsuario(idPartida)
        val idPrueba = ConexionCasilla.verTipoPrueba(idCasilla, idPartida)
        val casillasTotales = ConexionPartida.getCasillasTotales(idPartida)

        if (ConexionPartida.getEstadoPartida(idPartida) == "Ganada") {

            msg = "La partida ya ha sido ganada"
            cod = 400

        } else if (ConexionPartida.getEstadoPartida(idPartida) == "Perdida") {

            msg = "La partida ya ha sido perdida"
            cod = 400

        } else {

            ConexionPartida.actualizarCasillasDestapadas(idPartida, casillasTotales)
            ConexionPartida.actualizarEstadoPartida(idPartida, "En juego")

            if (idPrueba == 1) {
                val idGandalf = ConexionPersonaje.getPersonajeTipoPrueba(idUsuario, idPartida, idPrueba)
                realizarPrueba(idPartida, idCasilla, idGandalf)
                msg = "Prueba de magia"
                cod = 200
            } else if (idPrueba == 2) {
                val idThorin = ConexionPersonaje.getPersonajeTipoPrueba(idUsuario, idPartida, idPrueba)
                realizarPrueba(idPartida, idCasilla, idThorin)
                msg = "Prueba de fuerza"
                cod = 200
            } else if (idPrueba == 3) {
                val idBilbo = ConexionPersonaje.getPersonajeTipoPrueba(idUsuario, idPartida, idPrueba)
                realizarPrueba(idPartida, idCasilla, idBilbo)
                msg = "Prueba de habilidad"
                cod = 200
            } else {
                msg = "Error al buscar la prueba"
                cod = 400
            }
        }

        return Respuesta(msg, cod)
    }

    fun realizarPrueba(idPartida: Int, idCasilla: Int, idPersonaje: Int): Respuesta {
        var cod = 0
        var msg = ""

        val capacidadActual = ConexionPersonaje.getCapacidadActual(idPartida, idPersonaje)
        var capacidadNueva: Int = 0
        val idPrueba = ConexionCasilla.verTipoPrueba(idCasilla, idPartida)
        val esfuerzo = ConexionPrueba.getEsfuerzo(idPrueba)
        val random = Random.Default
        var superada: Boolean = false

        if (capacidadActual > esfuerzo) {
            if (random.nextInt(100) < 90) {
                capacidadNueva = capacidadActual - esfuerzo
                superada = true
            } else {
                capacidadNueva = 0
                superada = false
            }
        } else if (capacidadActual == esfuerzo) {
            if (random.nextInt(100) < 70) {
                capacidadNueva = capacidadActual - esfuerzo
                superada = true
            } else {
                capacidadNueva = 0
                superada = false
            }
        } else if (capacidadActual < esfuerzo) {
            if (random.nextInt(100) < 50) {
                capacidadNueva = capacidadActual - esfuerzo
                superada = true
            } else {
                capacidadNueva = 0
                superada = false
            }
        }

        if (superada) {
            ConexionPersonaje.actualizarCapacidadActual(idPartida, idPersonaje, capacidadNueva)
            ConexionCasilla.actualizarEstadoCasilla(idCasilla, idPartida, "Prueba superada")
            cod = 200
            msg = "Prueba realizada correctamente tiene $capacidadNueva de capacidad actual"
        } else {
            ConexionPersonaje.actualizarCapacidadActual(idPartida, idPersonaje, capacidadNueva)
            ConexionCasilla.actualizarEstadoCasilla(idCasilla, idPartida, "Prueba no superada")
            cod = 200
            msg = "Prueba fallida, el personaje tiene $capacidadNueva de capacidad actual"
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

    fun perderPartida(idPartida: Int): Boolean {
        val casillasDestapadas = ConexionPartida.getCasillasDestapadas(idPartida)
        val casillasTotales = ConexionPartida.getCasillasTotales(idPartida)
        var perder = false


        if (casillasTotales / 2 < casillasDestapadas) {
            perder = true
            ConexionPartida.actualizarEstadoPartida(idPartida, "Perdida")
        } else {
            perder = false
        }

        return perder
    }

}