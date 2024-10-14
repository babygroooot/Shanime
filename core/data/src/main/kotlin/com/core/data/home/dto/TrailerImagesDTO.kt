package com.core.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerImagesDTO(
    @SerialName("image_url")
    val imageURL: String?,

    @SerialName("small_image_url")
    val smallImageURL: String?,

    @SerialName("medium_image_url")
    val mediumImageURL: String?,

    @SerialName("large_image_url")
    val largeImageURL: String?,

    @SerialName("maximum_image_url")
    val maximumImageURL: String?,
)
