package com.feature.seasonal

data class SeasonalSharedElementKey(
    val id: Long,
    val content: String,
    val type: SeasonalSharedElementType,
)

enum class SeasonalSharedElementType {
    BANNER_IMAGE,
    TITLE,
    GENRE,
}
