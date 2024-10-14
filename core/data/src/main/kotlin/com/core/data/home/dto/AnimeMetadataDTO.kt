package com.core.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeMetadataDTO(
    @SerialName("mal_id")
    val malId: Long,
    val type: String,
    val name: String,
    val url: String,
)
