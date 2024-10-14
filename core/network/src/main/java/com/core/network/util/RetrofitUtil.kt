package com.core.network.util

import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitUtil @Inject constructor() {

    @Inject
    lateinit var retrofit: Retrofit

    fun <T> createApiService(service: Class<T>): T = retrofit.create(service)
}
