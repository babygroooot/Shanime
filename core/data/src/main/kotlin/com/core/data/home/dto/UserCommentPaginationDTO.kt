package com.core.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCommentPaginationDTO(
    @SerialName("last_visible_index")
    val lastVisibleIndex: Int? = null,
    @SerialName("has_next_page")
    val hasNextPage: Boolean,
)
