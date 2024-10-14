package com.core.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserImageDTO(
    @SerialName("image_url")
    val imageURL: String,
)
