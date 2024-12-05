package com.core.domain.home

import com.core.data.home.HomeRepository
import com.core.data.home.dto.TopMangaDTO
import com.core.model.home.AnimeMetadataModel
import com.core.model.home.ImageType
import com.core.model.home.TopMangaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopTenPublishingMangaUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {

    operator fun invoke(): Flow<Result<List<TopMangaModel>>> = homeRepository.getTopTenPublishingManga().map { result ->
        result.map { response ->
            response.data.map { it.toModel() }
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
