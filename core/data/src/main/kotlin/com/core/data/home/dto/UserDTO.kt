package com.core.data.home.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val url: String,
    val username: String,
    val images: Map<String, UserImageDTO>,
)
