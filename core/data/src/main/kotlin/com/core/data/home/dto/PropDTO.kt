package com.core.data.home.dto

import kotlinx.serialization.Serializable

@Serializable
data class PropDTO(
    val from: PropFromDTO,
    val to: PropToDTO,
)
