package model

import kotlinx.serialization.Serializable

@Serializable
data class Tablero (
    val casillas: Array<Casilla>
)