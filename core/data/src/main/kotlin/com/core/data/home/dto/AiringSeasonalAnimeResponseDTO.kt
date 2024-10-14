package com.core.data.home.dto

import com.core.data.PaginationDTO
import kotlinx.serialization.Serializable

@Serializable
data class AiringSeasonalAnimeResponseDTO(
    val pagination: PaginationDTO,
    val data: List<AiringSeasonalAnimeDTO>,
)
