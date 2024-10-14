package com.core.domain.discover

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.core.data.ITEM_LIMIT
import com.core.data.discover.DiscoverRepository
import com.core.data.home.dto.TopAnimeDTO
import com.core.model.home.AnimeMetadataModel
import com.core.model.home.ImageType
import com.core.model.home.TopAnimeModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository,
) {

    operator fun invoke(
        searchValue: String,
    ) = Pager(
        config = PagingConfig(
            pageSize = ITEM_LIMIT,
        ),
    ) {
        discoverRepository.searchAnimePagingSource(
            searchValue = searchValue,
        )
    }.flow.map { pagingData ->
        pagingData.map { topAnimeDTO ->
            topAnimeDTO.toModel()
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
