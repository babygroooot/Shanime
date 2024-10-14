package com.core.data.home.dto

import com.core.data.PaginationDTO
import kotlinx.serialization.Serializable

@Serializable
data class TopAnimeResponseDTO(
    val pagination: PaginationDTO,
    val data: List<TopAnimeDTO>,
)
