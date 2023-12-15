package database

import model.Casilla
import java.sql.SQLException

object ConexionCasilla {

    fun insertarCasilla(idPartida: Int, idTipoPrueba: Int, estadoPrueba: String): Int {
        var cod = 0

        val query =
            "INSERT INTO " + Constantes.TablaCasilla + "(idPartida, idTipoPrueba, estadoPrueba) VALUES (?, ?, ?)"

        try {
            Conexion.abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idPartida)
            ps.setInt(2, idTipoPrueba)
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

    fun getCasillaRandom(idPartida : Int) : Int {
        var cod = 0

        val query = "SELECT id FROM ${Constantes.TablaCasilla} WHERE idPartida = ? ORDER BY RAND() LIMIT 1"

        try {
            Conexion.abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idPartida)

            val rs = ps.executeQuery()

            while (rs.next()) {
                cod = rs.getInt("id")
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            Conexion.cerrarConexion()
        }

        return cod
    }
}