package com.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDTO(
    @SerialName("last_visible_index")
    val lastVisibleIndex: Int? = null,
    @SerialName("has_next_page")
    val hasNextPage: Boolean,
    @SerialName("current_page")
    val currentPage: Int,
    val items: PaginationItemsDTO,
)
