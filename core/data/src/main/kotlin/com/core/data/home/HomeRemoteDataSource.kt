package com.core.data.home

import com.core.data.ErrorDTO
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.data.home.dto.TopMangaResponseDTO
import com.core.data.home.dto.UserCommentResponseDTO
import com.core.network.util.NetworkResult

interface HomeRemoteDataSource {
    suspend fun getAiringSeasonalAnime(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO>

    suspend fun getTopAnime(
        type: String,
        filter: String,
        rating: String,
        sfw: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<TopAnimeResponseDTO, ErrorDTO>

    suspend fun getTopManga(
        type: String,
        filter: String,
        page: Int,
        limit: Int,
    ): NetworkResult<TopMangaResponseDTO, ErrorDTO>

    suspend fun getAnimeUserComments(
        id: Long,
        page: Int,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ): NetworkResult<UserCommentResponseDTO, ErrorDTO>

    suspend fun getMangaUserComments(
        id: Long,
        page: Int,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ): NetworkResult<UserCommentResponseDTO, ErrorDTO>
}
