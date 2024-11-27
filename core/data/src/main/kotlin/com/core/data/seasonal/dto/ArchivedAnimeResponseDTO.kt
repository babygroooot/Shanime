package com.core.data.seasonal.dto

import com.core.data.PaginationDTO
import com.core.data.home.dto.TopAnimeDTO
import kotlinx.serialization.Serializable

@Serializable
data class ArchivedAnimeResponseDTO(
    val pagination: PaginationDTO,
    val data: List<TopAnimeDTO>,
)
