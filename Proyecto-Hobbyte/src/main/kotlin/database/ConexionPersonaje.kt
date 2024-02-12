package database

import database.Conexion.abrirConexion
import database.Conexion.cerrarConexion
import model.Usuario
import java.sql.SQLException

object ConexionPersonaje {

    fun insertarPersonaje(nombre: String, idTipoPrueba: Int, capacidadMax: Int, idUsuario: Int): Int {
        var cod = 0

        val query =
            "INSERT INTO " + Constantes.TablaPersonajes + "(nombre, idTipoPrueba, capacidadMax, idUsuario) VALUES (?, ?, ?, ?)"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setString(1, nombre)
            ps.setInt(2, idTipoPrueba)
            ps.setInt(3, capacidadMax)
            ps.setInt(4, idUsuario)

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

    fun generarEstadoPj(idPartida: Int, idPersonaje: Int, capacidadActual: Int): Int {
        var cod = 0

        val query =
            "INSERT INTO " + Constantes.TablaEstadoPj + "(idPartida, idPersonaje, capacidadActual) VALUES (?, ?, ?)"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idPartida)
            ps.setInt(2, idPersonaje)
            ps.setInt(3, capacidadActual)

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