package com.core.domain.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.core.data.ITEM_LIMIT
import com.core.data.home.HomeRepository
import com.core.data.home.dto.TopMangaDTO
import com.core.model.home.AnimeMetadataModel
import com.core.model.home.ImageType
import com.core.model.home.TopMangaModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopMangaUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {

    operator fun invoke() = Pager(
        config = PagingConfig(
            pageSize = ITEM_LIMIT,
        ),
    ) {
        homeRepository.getTopManga()
    }.flow.map { pagingData ->
        pagingData.map { topMangaDTO ->
            topMangaDTO.toModel()
        }
    }

    private fun TopMangaDTO.toModel() = TopMangaModel(
        malId = malId,
        url = url,
        image = images[ImageType.JPG.value]?.largeImageUrl.orEmpty(),
        title = titleEnglish ?: titleJapanese.orEmpty(),
        synopsis = synopsis.orEmpty(),
        score = if (score == null) "N/A" else score.toString(),
        genres = genres.map { AnimeMetadataModel(malId = it.malId, type = it.type, name = it.name, url = it.url) },
        authorName = authors.firstOrNull()?.name.orEmpty(),
        isOnGoing = publishing,
        members = members,
        demographic = demographics.firstOrNull()?.name.orEmpty(),
    )
}
