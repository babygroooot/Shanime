package com.core.data.discover

import com.core.data.ErrorDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.network.util.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("type") type: String,
        @Query("filter") filter: String,
        @Query("rating") rating: String,
        @Query("sfw") sfw: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): NetworkResult<TopAnimeResponseDTO, ErrorDTO>

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") searchValue: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("unapproved") unapproved: Boolean,
    ): NetworkResult<TopAnimeResponseDTO, ErrorDTO>
}
