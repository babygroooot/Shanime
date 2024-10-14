package com.core.data.discover

import com.core.data.ErrorDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.network.util.NetworkResult

interface DiscoverRemoteDataSource {

    suspend fun getTopAnime(
        type: String,
        filter: String,
        rating: String,
        sfw: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<TopAnimeResponseDTO, ErrorDTO>

    suspend fun searchAnime(
        searchValue: String,
        page: Int,
        limit: Int,
    ): NetworkResult<TopAnimeResponseDTO, ErrorDTO>
}
