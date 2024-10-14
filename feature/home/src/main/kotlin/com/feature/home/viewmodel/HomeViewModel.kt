package com.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.OnetimeWhileSubscribed
import com.core.domain.home.GetHomeBannerUseCase
import com.core.domain.home.GetTopTenAiringAnimeUseCase
import com.core.domain.home.GetTopTenPublishingMangaUseCase
import com.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeBannerUseCase: GetHomeBannerUseCase,
    getTopTenAiringAnimeUseCase: GetTopTenAiringAnimeUseCase,
    getTopTenPublishingMangaUseCase: GetTopTenPublishingMangaUseCase,
) : ViewModel() {

    val homeUiState = combine(
        getHomeBannerUseCase(),
        getTopTenAiringAnimeUseCase(),
        getTopTenPublishingMangaUseCase(),
    ) { banner, anime, manga ->
        Triple(banner, anime, manga)
    }.map { (banner, anime, manga) ->
        if (banner.isSuccess.and(anime.isSuccess).and(manga.isSuccess)) {
            HomeUiState.Success(
                banners = banner.getOrThrow(),
                topAnime = anime.getOrThrow(),
                topManga = manga.getOrThrow(),
            )
        } else {
            HomeUiState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = OnetimeWhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = HomeUiState.Loading,
    )
}
