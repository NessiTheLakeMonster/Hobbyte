package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val nombre: String,
    val apellido: String,
    val email:String,
    val password: String
)