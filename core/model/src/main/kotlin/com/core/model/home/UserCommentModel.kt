package com.core.model.home

data class UserCommentModel(
    val id: Long,
    val userProfile: String,
    val username: String,
    val comment: String,
    val score: Int,
    val reactionCount: Int,
    val date: String,
)
