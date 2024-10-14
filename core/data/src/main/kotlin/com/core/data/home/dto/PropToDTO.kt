package com.core.data.home.dto

import kotlinx.serialization.Serializable

@Serializable
data class PropToDTO(
    val day: Int?,
    val month: Int?,
    val year: Int?,
)
