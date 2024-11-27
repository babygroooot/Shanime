package com.core.data.seasonal.dto

import kotlinx.serialization.Serializable

@Serializable
data class SeasonDTO(
    val year: Int,
    val seasons: List<String>,
)
