package controller

import database.ConexionPrueba
import factories.PruebaFactory
import io.ktor.http.*
import model.Prueba
import model.Respuesta

object PruebaController {

    fun addPruebas(): Respuesta {
        var cod = 0
        var msg = ""

        val factory = PruebaFactory()
        val pruebas = factory.generatePruebas()

        for (prueba in pruebas) {
            val insertado = ConexionPrueba.insertarPruebas(
                prueba.tipo,
                prueba.esfuerzo
            )

            if (insertado != 0) {
                msg = "Prueba insertada correctamente"
                cod = HttpStatusCode.Created.value
            } else {
                msg = "Error al insertar la prueba"
                cod = HttpStatusCode.BadRequest.value
            }
        }

        return Respuesta(msg, cod)
    }
}