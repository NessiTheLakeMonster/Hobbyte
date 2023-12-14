package utils

import kotlinx.serialization.Serializable

@Serializable
data class RevokeTokenRequest(
    val token: String
)