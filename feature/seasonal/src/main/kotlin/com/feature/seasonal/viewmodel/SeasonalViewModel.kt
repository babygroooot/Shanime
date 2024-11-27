package com.feature.seasonal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.core.domain.seasonal.GetAiringAnimeUseCase
import com.core.domain.seasonal.GetSeasonsUseCase
import com.core.domain.seasonal.GetUpcomingAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SeasonalViewModel @Inject constructor(
    getAiringAnimeUseCase: GetAiringAnimeUseCase,
    getUpcomingAnimeUseCase: GetUpcomingAnimeUseCase,
    getSeasonsUseCase: GetSeasonsUseCase,
) : ViewModel() {

    val airingAnime = getAiringAnimeUseCase(
        filter = "",
        sfw = true,
        unapproved = false,
        continuing = true,
    ).cachedIn(scope = viewModelScope)

    val upcomingAnime = getUpcomingAnimeUseCase(
        filter = "",
        sfw = true,
        unapproved = false,
        continuing = true,
    ).cachedIn(scope = viewModelScope)

    val seasons = getSeasonsUseCase().map { result ->
        if (result.isSuccess) {
            result.getOrThrow()
        } else {
            emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = emptyList(),
    )
}
