package com.core.network.util.authenticator

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponseDTO(
    val data: RefreshTokenDTO,
)
