package com.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDTO(
    val status: String,
    val type: String,
    val message: String,
    val error: String?,
    @SerialName("report_url")
    val reportUrl: String?,
)
