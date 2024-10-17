package com.feature.home.state

import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.home.TopAnimeModel
import com.core.model.home.TopMangaModel

sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Success(
        val banners: List<AiringSeasonalAnimeModel>,
        val topAnime: List<TopAnimeModel>,
        val topManga: List<TopMangaModel>,
    ) : HomeUiState

    data object Error : HomeUiState
}
