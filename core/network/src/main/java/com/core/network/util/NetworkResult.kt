package com.core.network.util

import com.core.common.R

sealed class NetworkResult<out T : Any, out E : Any> {
    class Success<T : Any>(val data: T) : NetworkResult<T, Nothing>()
    class Error<E : Any>(val code: Int, val message: String?, val errorData: E? = null) : NetworkResult<Nothing, E>()
    class Exception(val e: Throwable, val code: Int? = null) : NetworkResult<Nothing, Nothing>()
}

suspend fun <T : Any, E : Any> NetworkResult<T, E>.onSuccess(
    executable: suspend (T) -> Unit,
): NetworkResult<T, E> = apply {
    if (this is NetworkResult.Success<T>) {
        executable(data)
    }
}

suspend fun <T : Any, E : Any> NetworkResult<T, E>.onError(
    executable: suspend (code: Int, message: String?, errorData: E?) -> Unit,
): NetworkResult<T, E> = apply {
    if (this is NetworkResult.Error<E>) {
        executable(code, message, errorData)
    }
}

suspend fun <T : Any, E : Any> NetworkResult<T, E>.onException(
    executable: suspend (e: Throwable, code: Int?) -> Unit,
): NetworkResult<T, E> = apply {
    if (this is NetworkResult.Exception) {
        executable(e, code)
    }
}

fun parseHttpErrorRespond(code: Int): Int = when (code) {
    400 -> R.string.bad_request
    401 -> R.string.unauthorized
    404 -> R.string.error_not_found
    405 -> R.string.request_not_allowed
    500 -> R.string.server_error
    502 -> R.string.bad_gateway
    else -> R.string.something_went_wrong
}
