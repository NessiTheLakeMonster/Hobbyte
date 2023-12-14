package rutas

import controller.UsuarioController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Usuario
import modelo.Respuesta
import modelo.UsuarioLogin
import utils.TokenManager

fun Route.userRouting() {
    val tokenManager = TokenManager()

    route("/registrar") {
        post{
            val user = call.receive<Usuario>()
            UsuarioController.addUsuario(user)
            call.respondText("Usuario creado",status = HttpStatusCode.Created)
        }
    }

    route("/login") {
        post{
            val user = call.receive<UsuarioLogin>()
            val usuario = UsuarioController.getUsuario(user.email)


            val token = tokenManager.generateJWTToken(user)
            call.respond(mapOf("token" to token, "email" to user.email))
        }
    }

}
