package com.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.home.GetHomeBannerUseCase
import com.core.domain.home.GetTopTenAiringAnimeUseCase
import com.core.domain.home.GetTopTenPublishingMangaUseCase
import com.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeBannerUseCase: GetHomeBannerUseCase,
    getTopTenAiringAnimeUseCase: GetTopTenAiringAnimeUseCase,
    getTopTenPublishingMangaUseCase: GetTopTenPublishingMangaUseCase,
) : ViewModel() {

    private val refreshCount: MutableStateFlow<Int> = MutableStateFlow(0)

    val homeUiState = refreshCount.flatMapLatest {
        combine(
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
                HomeUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = HomeUiState.Loading,
    )

    fun onRetry() {
        refreshCount.update { count -> count.inc() }
    }
}
