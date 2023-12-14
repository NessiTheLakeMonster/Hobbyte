package controller

import database.ConexionUsuarios
import io.ktor.http.*
import modelo.Respuesta
import modelo.Usuario
import utils.Revoked

object UsuarioController {

    fun addUsuario(usuario: Usuario): Respuesta {
        var cod = 0

        var insertado = ConexionUsuarios.insertarPersona(
            usuario.nombre,
            usuario.apellido,
            usuario.email,
            usuario.password
        )

        var msg = ""

        if (insertado != 0) {
            msg = "Usuario insertado correctamente"
            cod = HttpStatusCode.Created.value
        } else {
            msg = "Error al insertar el usuario"
            cod = HttpStatusCode.BadRequest.value
        }

        return Respuesta(msg, cod)
    }

    fun getUsuario(email: String): Respuesta {
        var cod = 0

        var usuario = ConexionUsuarios.getUsuario(email)

        var msg = ""

        if (usuario != 0) {
            msg = "Usuario encontrado"
            cod = HttpStatusCode.OK.value
        } else {
            msg = "Error al encontrar el usuario"
            cod = HttpStatusCode.BadRequest.value
        }

        return Respuesta(msg, cod)
    }

    fun logout(token: String): Respuesta {
    var cod = 0

    // Remove the token from the list of active tokens
    val logoutSuccessful = Revoked.revoked.remove(token)

    var msg = ""

    if (logoutSuccessful) {
        msg = "Logout con exito"
        cod = HttpStatusCode.OK.value
    } else {
        msg = "Error al hacer logout"
        cod = HttpStatusCode.BadRequest.value
    }

    return Respuesta(msg, cod)
}

}