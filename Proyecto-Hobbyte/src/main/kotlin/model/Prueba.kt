package model

import kotlinx.serialization.Serializable

@Serializable
data class Prueba (
    val tipo: Int,
    val esfuerzo: Int,
)