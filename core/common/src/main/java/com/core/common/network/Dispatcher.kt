package com.core.common.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: ShanimeDispatcher)

enum class ShanimeDispatcher {
    Default,
    IO,
}
