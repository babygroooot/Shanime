package com.core.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class UserCommentDTO(
    @SerialName("mal_id")
    val malId: Long,
    val url: String,
    val type: String,
    val reactions: UserReactionDTO,
    val date: String,
    val review: String,
    val score: Int,
    val tags: List<String>,
    @SerialName("is_spoiler")
    val isSpoiler: Boolean,
    @SerialName("is_preliminary")
    val isPreliminary: Boolean,
    @SerialName("episodes_watched")
    val episodesWatched: JsonElement? = null,
    @SerialName("chapters_read")
    val chaptersRead: JsonElement? = null,
    val user: UserDTO,
)
