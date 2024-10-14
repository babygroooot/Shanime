package com.core.common.network.exception

import kotlin.Exception

class NetworkErrorException(message: String? = null, cause: Throwable? = null) :
    Exception(
        message,
        cause,
    ) {
    constructor(cause: Throwable) : this(message = null, cause = cause)
}
