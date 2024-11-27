package com.core.data.seasonal.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SeasonResponseDTO(
    val pagination: JsonElement,
    val data: List<SeasonDTO>,
)
