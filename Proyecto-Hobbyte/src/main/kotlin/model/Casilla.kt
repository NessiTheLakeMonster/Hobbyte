package model

import kotlinx.serialization.Serializable

@Serializable
data class Casilla(
    val idPartida: Int,
    val idTipoPrueba: Int,
    val estadoPrueba: String
)