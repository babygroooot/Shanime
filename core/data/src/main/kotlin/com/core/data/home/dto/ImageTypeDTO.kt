package com.core.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageTypeDTO(
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("small_image_url")
    val smallImageUrl: String,
    @SerialName("large_image_url")
    val largeImageUrl: String,
)
