package model

data class Partida(
    val idUsuario: Int,
    val estado: String,
    val casillas_totales: Int,
    val casillas_destapadas: Int,
)