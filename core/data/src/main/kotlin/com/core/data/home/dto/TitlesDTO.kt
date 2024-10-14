package com.core.data.home.dto

import kotlinx.serialization.Serializable

@Serializable
data class TitlesDTO(
    val type: String,
    val title: String,
)
