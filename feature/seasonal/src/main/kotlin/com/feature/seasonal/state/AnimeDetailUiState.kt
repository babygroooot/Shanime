package com.feature.seasonal.state

import com.core.model.home.UserCommentModel

sealed interface AnimeDetailUiState {

    data object Loading : AnimeDetailUiState

    data class Success(
        val commentList: List<UserCommentModel>,
    ) : AnimeDetailUiState

    data object Error : AnimeDetailUiState
}
