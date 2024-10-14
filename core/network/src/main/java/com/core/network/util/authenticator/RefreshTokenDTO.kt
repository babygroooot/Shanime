package com.core.network.util.authenticator

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDTO(
    val id: Int,
    val accessToken: String,
    val refreshToken: String,
)
