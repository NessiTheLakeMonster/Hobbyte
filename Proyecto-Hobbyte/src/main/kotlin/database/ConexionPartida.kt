package database

import java.sql.*
import database.Conexion.abrirConexion
import database.Conexion.cerrarConexion

object ConexionPartida {

    fun crearPartida(idUsuario: Int, estado: String, casillas_totales: Int, casillas_destapadas: Int): Int {
        var cod = 0

        val query =
            "INSERT INTO " + Constantes.TablaPartida + "(idUsuario, estado, casillas_totales, casillas_destapadas) VALUES (?, ?, ?, ?)"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idUsuario)
            ps.setString(2, estado)
            ps.setInt(3, casillas_totales)
            ps.setInt(4, casillas_destapadas)

            cod = ps.executeUpdate()
            ps.close()
        } catch (sq: SQLException) {
            println("Error al insertar en la BD" + sq.message)
            cod = sq.errorCode
        } finally {
            cerrarConexion()
        }

        return cod
    }

    fun actualizarCasillasDestapadas(idPartida: Int, casillas_destapadas: Int): Int {
        var cod = 0

        val query = "UPDATE " + Constantes.TablaPartida + " SET casillas_destapadas = ? WHERE id = ?"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, casillas_destapadas)
            ps.setInt(2, idPartida)

            cod = ps.executeUpdate()
            ps.close()
        } catch (sq: SQLException) {
            println("Error al actualizar en la BD" + sq.message)
            cod = sq.errorCode
        } finally {
            cerrarConexion()
        }

        return cod
    }

    fun getCasillasTotales(idPartida: Int): Int {
        var casillas = 0

        val query = "SELECT casillas_totales FROM " + Constantes.TablaPartida + " WHERE id = ?"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idPartida)

            val rs = ps.executeQuery()

            while (rs.next()) {
                casillas = rs.getInt("casillas_totales")
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            cerrarConexion()
        }

        return casillas
    }

    fun getCasillasDestapadas(idPartida: Int): Int {
        var casillas = 0

        val query = "SELECT casillas_destapadas FROM " + Constantes.TablaPartida + " WHERE id = ?"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idPartida)

            val rs = ps.executeQuery()

            while (rs.next()) {
                casillas = rs.getInt("casillas_destapadas")
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            cerrarConexion()
        }

        return casillas
    }

    fun actualizarEstadoPartida(idPartida: Int, estado: String): Int {
        var cod = 0

        val query = "UPDATE " + Constantes.TablaPartida + " SET estado = ? WHERE id = ?"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setString(1, estado)
            ps.setInt(2, idPartida)

            cod = ps.executeUpdate()
            ps.close()
        } catch (sq: SQLException) {
            println("Error al actualizar en la BD" + sq.message)
            cod = sq.errorCode
        } finally {
            cerrarConexion()
        }

        return cod
    }
}