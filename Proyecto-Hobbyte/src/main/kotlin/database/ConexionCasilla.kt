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

    fun verTipoPrueba(idPartida: Int): ArrayList<Int> {
        val ids = ArrayList<Int>()

        val query = "SELECT idPrueba FROM ${Constantes.TablaCasilla} WHERE idPartida = ?"

        try {
            Conexion.abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idPartida)

            val rs = ps.executeQuery()

            while (rs.next()) {
                ids.add(rs.getInt("idPrueba"))
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            Conexion.cerrarConexion()
        }

        return ids
    }
}