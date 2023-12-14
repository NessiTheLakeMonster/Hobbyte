package database

import database.Conexion.abrirConexion
import database.Conexion.cerrarConexion
import java.sql.*

object ConexionPrueba {

    fun insertarPruebas(tipo: String, esfuerzo: Int) : Int {
        val cod = 0

        val query = "INSERT INTO ${Constantes.TablaPruebas} (tipo, esfuerzo) VALUES (?, ?)"

        try {
            abrirConexion()
            val ps = Conexion.conexion!!.prepareStatement(query)

            ps.setString(1, tipo)
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
}