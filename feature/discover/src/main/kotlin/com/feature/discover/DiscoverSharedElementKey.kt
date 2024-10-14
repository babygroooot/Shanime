package com.feature.discover

data class DiscoverSharedElementKey(
    val id: Long,
    val content: String,
    val type: DiscoverSharedElementType,
)

enum class DiscoverSharedElementType {
    BANNER_IMAGE,
    BANNER_BACKGROUND,
    TITLE,
    GENRE,
}
