package com.core.data.seasonal

import com.core.data.ErrorDTO
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.network.util.NetworkResult

interface SeasonalRemoteDataSource {

    suspend fun getAiringAnime(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO>

    suspend fun getUpcomingAnime(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO>
}
