package com.core.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerDTO(
    @SerialName("youtube_id")
    val youtubeId: String?,

    val url: String?,

    @SerialName("embed_url")
    val embedUrl: String?,

    val images: TrailerImagesDTO,
)
