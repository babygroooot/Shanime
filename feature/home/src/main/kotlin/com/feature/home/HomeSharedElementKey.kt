package com.feature.home

data class HomeSharedElementKey(
    val id: Long,
    val content: String,
    val type: HomeSharedElementType,
)

enum class HomeSharedElementType {
    BANNER_IMAGE,
    BANNER_BACKGROUND,
    TITLE,
    GENRE,
    LAYOUT,
}
