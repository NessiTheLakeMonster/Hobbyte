package database

import database.Conexion.abrirConexion
import database.Conexion.cerrarConexion
import java.sql.*

object ConexionPrueba {

    fun insertarPruebas(tipo: Int, esfuerzo: Int) : Int {
        val cod = 0

        val query = "INSERT INTO ${Constantes.TablaPruebas} (tipo, esfuerzo) VALUES (?, ?)"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setInt(1, tipo)
            ps.setInt(2, esfuerzo)

            ps.executeUpdate()
            ps.close()
        } catch (sq: SQLException) {
            println("Error al insertar en la BD" + sq.message)
        } finally {
            cerrarConexion()
        }

        return cod
    }

    fun getIdPruebas(): ArrayList<Int> {
        val ids = ArrayList<Int>()

        val query = "SELECT id FROM ${Constantes.TablaPruebas}"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

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
}