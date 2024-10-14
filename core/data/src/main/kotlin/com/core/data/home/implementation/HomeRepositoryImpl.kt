package com.core.data.home.implementation

import com.core.common.network.Dispatcher
import com.core.common.network.ShanimeDispatcher
import com.core.common.network.exception.NetworkErrorException
import com.core.data.home.HomeRemoteDataSource
import com.core.data.home.HomeRepository
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.data.home.dto.TopMangaResponseDTO
import com.core.data.home.dto.UserCommentResponseDTO
import com.core.network.util.onError
import com.core.network.util.onException
import com.core.network.util.onSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    @Dispatcher(ShanimeDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : HomeRepository {
    override fun getHomeBanner(): Flow<Result<AiringSeasonalAnimeResponseDTO>> = flow<Result<AiringSeasonalAnimeResponseDTO>> {
        val request = homeRemoteDataSource.getAiringSeasonalAnime(
            filter = "tv",
            sfw = true,
            unapproved = false,
            continuing = true,
            page = 1,
            limit = 5,
        )
        request.onSuccess { response ->
            emit(Result.success(response))
        }
        request.onError { _, _, errorData ->
            emit(Result.failure(NetworkErrorException(message = errorData?.message.orEmpty())))
        }
        request.onException { e, _ ->
            emit(Result.failure(e))
        }
    }.flowOn(context = ioDispatcher)

    override fun getTopTenAiringAnime(): Flow<Result<TopAnimeResponseDTO>> = flow<Result<TopAnimeResponseDTO>> {
        val request = homeRemoteDataSource.getTopAnime(
            type = "tv",
            filter = "airing",
            rating = "",
            sfw = true,
            page = 1,
            limit = 10,
        )
        request.onSuccess { response ->
            emit(Result.success(response))
        }
        request.onError { _, _, errorData ->
            emit(Result.failure(NetworkErrorException(message = errorData?.message.orEmpty())))
        }
        request.onException { e, _ ->
            emit(Result.failure(e))
        }
    }.flowOn(context = ioDispatcher)

    override fun getTopTenPublishingManga(): Flow<Result<TopMangaResponseDTO>> = flow<Result<TopMangaResponseDTO>> {
        val request = homeRemoteDataSource.getTopManga(
            type = "",
            filter = "bypopularity",
            page = 1,
            limit = 10,
        )
        request.onSuccess { response ->
            emit(Result.success(response))
        }
        request.onError { _, _, errorData ->
            emit(Result.failure(NetworkErrorException(message = errorData?.message.orEmpty())))
        }
        request.onException { e, _ ->
            emit(Result.failure(e))
        }
    }.flowOn(context = ioDispatcher)

    override fun getAnimeUserCommentsPreview(
        id: Long,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ): Flow<Result<UserCommentResponseDTO>> = flow<Result<UserCommentResponseDTO>> {
        val request = homeRemoteDataSource.getAnimeUserComments(
            id = id,
            page = 1,
            isPreliminary = isPreliminary,
            isSpoiler = isSpoiler,
        )
        request.onSuccess { response ->
            emit(Result.success(response))
        }
        request.onError { _, _, errorData ->
            emit(Result.failure(NetworkErrorException(message = errorData?.message.orEmpty())))
        }
        request.onException { e, _ ->
            emit(Result.failure(e))
        }
    }.flowOn(context = ioDispatcher)

    override fun getMangaUserComments(
        id: Long,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ): Flow<Result<UserCommentResponseDTO>> = flow<Result<UserCommentResponseDTO>> {
        val request = homeRemoteDataSource.getMangaUserComments(
            id = id,
            page = 1,
            isPreliminary = isPreliminary,
            isSpoiler = isSpoiler,
        )
        request.onSuccess { response ->
            emit(Result.success(response))
        }
        request.onError { _, _, errorData ->
            emit(Result.failure(NetworkErrorException(message = errorData?.message.orEmpty())))
        }
        request.onException { e, _ ->
            emit(Result.failure(e))
        }
    }.flowOn(context = ioDispatcher)
}
