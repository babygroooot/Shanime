package com.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.core.common.OnetimeWhileSubscribed
import com.core.domain.home.GetMangaUserCommentPreviewUseCase
import com.feature.home.HomeDestinations
import com.feature.home.state.MangaDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    getMangaUserCommentPreviewUseCase: GetMangaUserCommentPreviewUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val args = savedStateHandle.toRoute<HomeDestinations.MangaDetail>()

    val mangaDetailUiState = getMangaUserCommentPreviewUseCase(
        id = args.id,
        isPreliminary = true,
        isSpoiler = false,
    ).map { result ->
        if (result.isSuccess) {
            MangaDetailUiState.Success(
                commentList = result.getOrThrow().take(n = 5),
            )
        } else {
            MangaDetailUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = OnetimeWhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = MangaDetailUiState.Loading,
    )
}
