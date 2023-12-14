package database

import java.sql.*
import database.Conexion.abrirConexion
import database.Conexion.cerrarConexion

object  ConexionUsuarios {

    fun insertarPersona(nombre: String, apellido: String, email: String, password: String): Int {
        var cod = 0

        val query = "INSERT INTO " +  Constantes.TablaUsuarios + "(nombre, apellido, email, password) VALUES (?, ?, ?, ?)"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setString(1, nombre)
            ps.setString(2, apellido)
            ps.setString(3, email)
            ps.setString(4, password)

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

    fun getUsuario(email: String):Int {
        var cod = 0

        val query = "SELECT * FROM" + Constantes.TablaUsuarios + "WHERE email = ?"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setString(1, email)

            cod = ps.executeUpdate()
            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
            cod = sq.errorCode
        } finally {
            cerrarConexion()
        }

        return cod
    }
}