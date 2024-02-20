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

    fun getPersonajeUsuarioPartida(idUsuario: Int, idPartida: Int): List<Int> {
        val ids = ArrayList<Int>()

        val query = "SELECT " + Constantes.TablaPersonajes + ".id FROM " + Constantes.TablaPersonajes + " JOIN " +
                Constantes.TablaUsuarios + " ON " + Constantes.TablaPersonajes + ".idUsuario = " + Constantes.TablaUsuarios +
                ".id JOIN " + Constantes.TablaPartida + " ON " + Constantes.TablaUsuarios + ".id = " + Constantes.TablaPartida +
                ".idUsuario WHERE " + Constantes.TablaUsuarios + ".id = ? AND " + Constantes.TablaPartida + ".id = ?"
        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idUsuario)
            ps.setInt(2, idPartida)

            val rs = ps.executeQuery()

            while (rs.next()) {
                ids.add(rs.getInt("id"))
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            cerrarConexion()
        }

        return ids
    }

    fun getPersonajeTipoPrueba(idUsuario: Int, idPartida: Int, idTipoPrueba: Int): Int {
        var id = 0

        val query = "SELECT " + Constantes.TablaPersonajes + ".id FROM " + Constantes.TablaPersonajes + " JOIN " +
                Constantes.TablaUsuarios + " ON " + Constantes.TablaPersonajes + ".idUsuario = " + Constantes.TablaUsuarios +
                ".id JOIN " + Constantes.TablaPartida + " ON " + Constantes.TablaUsuarios + ".id = " + Constantes.TablaPartida +
                ".idUsuario WHERE " + Constantes.TablaUsuarios + ".id = ? AND " + Constantes.TablaPartida + ".id = ? AND " +
                Constantes.TablaPersonajes + ".idTipoPrueba = ?"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idUsuario)
            ps.setInt(2, idPartida)
            ps.setInt(3, idTipoPrueba)

            val rs = ps.executeQuery()

            if (rs.next()) {
                id = rs.getInt("id")
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            cerrarConexion()
        }

        return id
    }

    fun getCapacidadActual(idPartida: Int, idPersonaje: Int): Int {
        var ids = 0

        val query =
            "SELECT capacidadActual FROM " + Constantes.TablaEstadoPj + " WHERE idPartida = ? AND idPersonaje = ?"
        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, idPartida)
            ps.setInt(2, idPersonaje)

            val rs = ps.executeQuery()

            if (rs.next()) {
                ids = rs.getInt("capacidadActual")
            }

            ps.close()
        } catch (sq: SQLException) {
            println("Error al buscar en la BD" + sq.message)
        } finally {
            cerrarConexion()
        }

        return ids
    }

    fun actualizarCapacidadActual(idPartida: Int, idPersonaje: Int, capacidadActual: Int): Int {
        var cod = 0

        val query =
            "UPDATE " + Constantes.TablaEstadoPj + " SET capacidadActual = ? WHERE idPartida = ? AND idPersonaje = ?"
        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, capacidadActual)
            ps.setInt(2, idPartida)
            ps.setInt(3, idPersonaje)

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