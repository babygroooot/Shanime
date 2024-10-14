package com.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationItemsDTO(
    val count: Int,
    val total: Int,
    @SerialName("per_page")
    val perPage: Int,
)
