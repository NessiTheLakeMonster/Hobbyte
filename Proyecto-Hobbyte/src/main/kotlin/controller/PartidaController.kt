package controller

import database.ConexionCasilla
import database.ConexionPartida
import database.ConexionPersonaje
import database.ConexionPrueba
import io.ktor.http.*
import model.Partida
import model.RespuestaConTablero

object PartidaController {

    fun addPartida(partida: Partida): RespuestaConTablero {
        var cod = 0

        // Se inserta la partida
        var insertado = ConexionPartida.crearPartida(
            partida.idUsuario,
            partida.estado,
            partida.casillas_totales,
            partida.casillas_destapadas
        )

        // Se insertan los personajes

        var msg = ""
        var tablero = ArrayList<Char>()

        if (insertado != 0) {
            msg = "Partida insertada correctamente"
            cod = HttpStatusCode.Created.value
            tablero = pintarTablero(partida.casillas_totales, partida.casillas_destapadas)
        } else {
            msg = "Error al insertar la partida"
            cod = HttpStatusCode.BadRequest.value
            tablero = pintarTablero(partida.casillas_totales, partida.casillas_destapadas)
        }

        return RespuestaConTablero(msg, cod, tablero)
    }

    fun insertarCasillas(idPartida: Int): RespuestaConTablero {
        var cod = 0
        var msg = ""
        var tablero = ArrayList<Char>()


        val casillas_totales = ConexionPartida.getCasillasTotales(idPartida)
        val idPruebas = ConexionPrueba.getIdPruebas().shuffled()

        for (i in 0 until casillas_totales) {
            val id = idPruebas[i % idPruebas.size]
            ConexionCasilla.insertarCasilla(idPartida, id, "Prueba no comenzada")
        }

        if (casillas_totales != 0) {
            msg = "Casillas insertadas correctamente"
            cod = HttpStatusCode.Created.value
            tablero = pintarTablero(casillas_totales, ConexionPartida.getCasillasDestapadas(idPartida))
        } else {
            msg = "Error al insertar las casillas"
            cod = HttpStatusCode.BadRequest.value
            tablero = pintarTablero(casillas_totales, ConexionPartida.getCasillasDestapadas(idPartida))
        }

        return RespuestaConTablero(msg, cod, tablero)
    }

    fun pintarTablero(casillas_totales: Int, casillas_destapadas: Int): ArrayList<Char> {
        val tablero = ArrayList<Char>()

        for (i in 0 until casillas_totales) {
            if (i < casillas_destapadas) {
                tablero.add('O')
            } else {
                tablero.add('X')
            }
        }

        return tablero
    }

    fun verTablero(idPartida: Int): RespuestaConTablero {
        var cod = 0
        var msg = ""
        var tablero = ArrayList<Char>()
        val casillas_totales = ConexionPartida.getCasillasTotales(idPartida)
        val casillas_destapadas = ConexionPartida.getCasillasDestapadas(idPartida)

        if (casillas_totales != 0) {
            msg = "Tablero obtenido correctamente"
            cod = HttpStatusCode.OK.value
            tablero = pintarTablero(casillas_totales, casillas_destapadas)
        } else {
            msg = "Error al obtener el tablero"
            cod = HttpStatusCode.BadRequest.value
            tablero = pintarTablero(casillas_totales, casillas_destapadas)
        }

        return RespuestaConTablero(msg, cod, tablero)
    }

    fun generarPartida(idPartida: Int): List<Int> {
        val ids = PruebaController.getIdPruebas()
        ids.shuffled()

        return ids
    }

    fun generarPartidaCustom(idPartida: Int, casillas_totales: Int): List<Int> {
        val ids = PruebaController.getIdPruebas()
        ids.shuffled()

        return ids
    }

    fun verCasillasTotales(idPartida: Int): Int {
        return ConexionPartida.getCasillasTotales(idPartida)
    }
}