package com.core.data.home

import com.core.data.ErrorDTO
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.data.home.dto.TopMangaResponseDTO
import com.core.data.home.dto.UserCommentResponseDTO
import com.core.network.util.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    @GET("seasons/now")
    suspend fun getAiringSeasonalAnime(
        @Query("filter") filter: String,
        @Query("sfw") sfw: Boolean,
        @Query("unapproved") unapproved: Boolean,
        @Query("continuing") continuing: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO>

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("type") type: String,
        @Query("filter") filter: String,
        @Query("rating") rating: String,
        @Query("sfw") sfw: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): NetworkResult<TopAnimeResponseDTO, ErrorDTO>

    @GET("top/manga")
    suspend fun getTopManga(
        @Query("type") type: String,
        @Query("filter") filter: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): NetworkResult<TopMangaResponseDTO, ErrorDTO>

    @GET("anime/{id}/reviews")
    suspend fun getAnimeUserComments(
        @Path("id") id: Long,
        @Query("page") page: Int,
        @Query("preliminary") isPreliminary: Boolean,
        @Query("spoilers") isSpoiler: Boolean,
    ): NetworkResult<UserCommentResponseDTO, ErrorDTO>

    @GET("manga/{id}/reviews")
    suspend fun getMangaUserComments(
        @Path("id") id: Long,
        @Query("page") page: Int,
        @Query("preliminary") isPreliminary: Boolean,
        @Query("spoilers") isSpoiler: Boolean,
    ): NetworkResult<UserCommentResponseDTO, ErrorDTO>
}
