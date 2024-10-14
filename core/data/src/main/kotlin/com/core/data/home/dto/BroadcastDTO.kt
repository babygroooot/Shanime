package com.core.data.home.dto

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastDTO(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?,
)
