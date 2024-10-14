package com.core.data.seasonal

import com.core.data.ErrorDTO
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.network.util.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SeasonalService {

    @GET("seasons/now")
    suspend fun getAiringSeasonalAnime(
        @Query("filter") filter: String,
        @Query("sfw") sfw: Boolean,
        @Query("unapproved") unapproved: Boolean,
        @Query("continuing") continuing: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO>

    @GET("seasons/upcoming")
    suspend fun getUpcomingAnime(
        @Query("filter") filter: String,
        @Query("sfw") sfw: Boolean,
        @Query("unapproved") unapproved: Boolean,
        @Query("continuing") continuing: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO>
}
