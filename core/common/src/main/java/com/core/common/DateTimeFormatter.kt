package com.core.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.formatDate(
    inputFormat: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME,
    outputPattern: String,
): String {
    val outputFormat = DateTimeFormatter.ofPattern(outputPattern)
    val inputDate = LocalDateTime.parse(this, inputFormat)
    return inputDate.format(outputFormat)
}
