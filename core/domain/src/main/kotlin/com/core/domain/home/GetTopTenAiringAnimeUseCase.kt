package com.core.domain.home

import com.core.data.home.HomeRepository
import com.core.data.home.dto.TopAnimeDTO
import com.core.model.home.AnimeMetadataModel
import com.core.model.home.ImageType
import com.core.model.home.TopAnimeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopTenAiringAnimeUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {

    operator fun invoke(): Flow<Result<List<TopAnimeModel>>> = flow {
        homeRepository.getTopTenAiringAnime().collect { networkResult ->
            networkResult.onSuccess { response ->
                emit(Result.success(response.data.map { it.toModel() }))
            }
        }
    }

    private fun TopAnimeDTO.toModel() = TopAnimeModel(
        malId = malId,
        url = url,
        image = images[ImageType.JPG.value]?.largeImageUrl.orEmpty(),
        title = titleEnglish ?: titleJapanese.orEmpty(),
        synopsis = synopsis.orEmpty(),
        score = if (score == null) "N/A" else score.toString(),
        genres = genres.map { AnimeMetadataModel(malId = it.malId, type = it.type, name = it.name, url = it.url) },
        year = if (year == null) "N/A" else year.toString(),
        season = season ?: "N/A",
        isAiring = airing,
        members = members,
        trailerVideoId = trailer.youtubeId.orEmpty(),
        rating = rating ?: "N/A",
    )
}
