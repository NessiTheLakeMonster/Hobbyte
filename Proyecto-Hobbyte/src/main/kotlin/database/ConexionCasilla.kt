package database

import model.Casilla
import java.sql.SQLException

object ConexionCasilla {

    fun insertarCasilla(idPartida: Int, idPrueba: Int, estadoPrueba: String): Int {
        var cod = 0

        val query =
            "INSERT INTO " + Constantes.TablaCasilla + "(idPartida, idPrueba, estadoPrueba) VALUES (?, ?, ?)"

        try {
            Conexion.abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idPartida)
            ps.setInt(2, idPrueba)
            ps.setString(3, estadoPrueba)

            ps.executeUpdate()
            ps.close()
        } catch (sq: SQLException) {
            println("Error al insertar en la BD" + sq.message)
            cod = sq.errorCode
        } finally {
            Conexion.cerrarConexion()
        }

        return cod
    }

    fun verPruebaCasilla(idCasilla: Int, idPartida: Int): Int {
        var ids = 0

        val query =
            "SELECT idPrueba FROM " + Constantes.TablaCasilla + " WHERE id = ? AND idPartida = ?"

        try {
            Conexion.abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idCasilla)
            ps.setInt(2, idPartida)

            val rs = ps.executeQuery()

            if (rs.next()) {
                ids = rs.getInt("idPrueba")
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            Conexion.cerrarConexion()
        }

        return ids
    }

    fun verTipoPrueba(idCasilla: Int): Int {
        var id = 0

        val query =
            "SELECT " + Constantes.TablaPruebas + ".idTipoPrueba FROM " + Constantes.TablaPruebas + " JOIN " + Constantes.TablaCasilla + " ON " +
                    Constantes.TablaPruebas + ".id = " + Constantes.TablaCasilla + ".idPrueba WHERE " +
                    Constantes.TablaCasilla + ".id = ?"

        try {
            Conexion.abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idCasilla)

            val rs = ps.executeQuery()

            if (rs.next()) {
                id = rs.getInt("idTipoPrueba")
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            Conexion.cerrarConexion()
        }

        return id
    }

    fun actualizarEstadoCasilla(idCasilla: Int, idPartida: Int, estado: String): Int {
        var cod = 0

        val query =
            "UPDATE " + Constantes.TablaCasilla + " SET estadoPrueba = ? WHERE id = ? AND idPartida = ?"

        try {
            Conexion.abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setString(1, estado)
            ps.setInt(2, idCasilla)
            ps.setInt(3, idPartida)

            cod = ps.executeUpdate()
            ps.close()
        } catch (sq: SQLException) {
            println("Error al actualizar en la BD" + sq.message)
            cod = sq.errorCode
        } finally {
            Conexion.cerrarConexion()
        }

        return cod
    }
}