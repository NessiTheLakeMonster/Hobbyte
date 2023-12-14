package database

import java.sql.*
import database.Conexion.abrirConexion
import database.Conexion.cerrarConexion

object ConexionPartida {

    fun crearPartida(idUsuario: Int, estado: String): Int {
        var cod = 0

        val query = "INSERT INTO " + Constantes.TablaPartida + "(idUsuario, estado) VALUES (?, ?)"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idUsuario)
            ps.setString(2, estado)

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
}