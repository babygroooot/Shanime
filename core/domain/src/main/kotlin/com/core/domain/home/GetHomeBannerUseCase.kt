package com.core.domain.home

import com.core.data.home.HomeRepository
import com.core.data.home.dto.AiringSeasonalAnimeDTO
import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.home.AnimeMetadataModel
import com.core.model.home.ImageType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHomeBannerUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {

    operator fun invoke(): Flow<Result<List<AiringSeasonalAnimeModel>>> = flow {
        homeRepository.getHomeBanner().collect { networkResult ->
            networkResult.onSuccess { response ->
                emit(Result.success(response.data.map { it.toModel() }))
            }
            networkResult.onFailure { throwable ->
                emit(Result.failure(exception = throwable))
            }
        }
    }

    private fun AiringSeasonalAnimeDTO.toModel() = AiringSeasonalAnimeModel(
        malId = malId,
        url = url,
        image = images[ImageType.JPG.value]?.largeImageUrl.orEmpty(),
        title = titleEnglish ?: titleJapanese.orEmpty(),
        synopsis = synopsis.orEmpty(),
        genres = genres.map { AnimeMetadataModel(malId = it.malId, type = it.type, name = it.name, url = it.url) },
        score = score.toString(),
        members = members,
        year = year ?: 0,
        rating = rating.orEmpty(),
        isAiring = airing,
        trailerVideoId = trailer.youtubeId.orEmpty(),
    )
}
