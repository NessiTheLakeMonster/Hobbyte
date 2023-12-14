package model

import kotlinx.serialization.Serializable

@Serializable
data class Prueba (
    val tipo: String,
    val esfuerzo: Int,
)