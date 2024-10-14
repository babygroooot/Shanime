package com.feature.home.state

import com.core.model.home.UserCommentModel

interface MangaDetailUiState {
    data object Loading : MangaDetailUiState

    data class Success(
        val commentList: List<UserCommentModel>,
    ) : MangaDetailUiState

    data object Error : MangaDetailUiState
}
