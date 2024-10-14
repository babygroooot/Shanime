package com.core.data.home.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiredDTO(
    val from: String?,
    val to: String? = null,
    val prop: PropDTO,
    val string: String,
)
