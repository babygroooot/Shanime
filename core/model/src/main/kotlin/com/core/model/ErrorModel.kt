package com.core.model

data class ErrorModel(
    val status: Int,
    val type: String,
    val message: String,
    val error: String,
    val reportUrl: String,
)
