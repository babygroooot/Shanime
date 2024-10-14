package com.core.data.home.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserCommentResponseDTO(
    val pagination: UserCommentPaginationDTO,
    val data: List<UserCommentDTO>,
)
