package model

import kotlinx.serialization.Serializable

@Serializable
data class RespuestaConTablero(
    val message: String,
    val status: Int,
    val tablero: ArrayList<Char>
)