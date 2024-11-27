package com.core.data.seasonal

import com.core.data.ErrorDTO
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.data.seasonal.dto.ArchivedAnimeResponseDTO
import com.core.data.seasonal.dto.SeasonResponseDTO
import com.core.network.util.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("seasons")
    suspend fun getSeasons(): NetworkResult<SeasonResponseDTO, ErrorDTO>

    @GET("seasons/{year}/{season}")
    suspend fun getArchivedAnime(
        @Path("year") year: String,
        @Path("season") season: String,
        @Query("sfw") sfw: Boolean,
        @Query("unapproved") unapproved: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): NetworkResult<ArchivedAnimeResponseDTO, ErrorDTO>
}
