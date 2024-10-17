package com.core.domain.seasonal

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.core.data.ITEM_LIMIT
import com.core.data.home.dto.AiringSeasonalAnimeDTO
import com.core.data.seasonal.SeasonalRepository
import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.home.AnimeMetadataModel
import com.core.model.home.ImageType
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAiringAnimeUseCase @Inject constructor(
    private val seasonalRepository: SeasonalRepository,
) {
    operator fun invoke(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
    ) = Pager(
        config = PagingConfig(
            pageSize = ITEM_LIMIT,
            enablePlaceholders = false,
        ),
    ) {
        seasonalRepository.getAiringAnimePagingSource(
            filter = filter,
            sfw = sfw,
            unapproved = unapproved,
            continuing = continuing,
        )
    }.flow.map { pagingData ->
        pagingData.map { airingSeasonalAnimeDTO ->
            airingSeasonalAnimeDTO.toModel()
        }
    }

    private fun AiringSeasonalAnimeDTO.toModel() = AiringSeasonalAnimeModel(
        malId = malId,
        url = url,
        image = images[ImageType.JPG.value]?.largeImageUrl.orEmpty(),
        title = titleEnglish ?: titleJapanese.orEmpty(),
        synopsis = synopsis.orEmpty(),
        genres = genres.map { AnimeMetadataModel(malId = it.malId, type = it.type, name = it.name, url = it.url) },
        score = if (score == null) "N/A" else score.toString(),
        members = members,
        year = year ?: 0,
        rating = rating.orEmpty(),
        isAiring = airing,
        trailerVideoId = trailer.youtubeId.orEmpty(),
    )
}
