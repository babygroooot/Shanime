package com.feature.seasonal.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.core.common.OnetimeWhileSubscribed
import com.core.domain.home.GetAnimeUserCommentPreviewUseCase
import com.feature.seasonal.SeasonalDestinations
import com.feature.seasonal.state.AnimeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    getAnimeUserCommentPreviewUseCase: GetAnimeUserCommentPreviewUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<SeasonalDestinations.AnimeDetail>()

    val animeDetailUiState = getAnimeUserCommentPreviewUseCase(
        id = args.id,
        isPreliminary = true,
        isSpoiler = false,
    ).map { result ->
        if (result.isSuccess) {
            AnimeDetailUiState.Success(commentList = result.getOrThrow().take(n = 3))
        } else {
            AnimeDetailUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = OnetimeWhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = AnimeDetailUiState.Loading,
    )
}
