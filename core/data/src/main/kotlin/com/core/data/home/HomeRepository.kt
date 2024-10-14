package com.core.data.home

import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.data.home.dto.TopMangaResponseDTO
import com.core.data.home.dto.UserCommentResponseDTO
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getHomeBanner(): Flow<Result<AiringSeasonalAnimeResponseDTO>>

    fun getTopTenAiringAnime(): Flow<Result<TopAnimeResponseDTO>>

    fun getTopTenPublishingManga(): Flow<Result<TopMangaResponseDTO>>

    fun getAnimeUserCommentsPreview(
        id: Long,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ): Flow<Result<UserCommentResponseDTO>>

    fun getMangaUserComments(
        id: Long,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ): Flow<Result<UserCommentResponseDTO>>
}
