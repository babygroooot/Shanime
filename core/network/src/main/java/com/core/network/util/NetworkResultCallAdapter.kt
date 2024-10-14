package com.core.network.util

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkResultCallAdapter<T : Any, E : Any>(
    private val resultType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>,
) : CallAdapter<T, Call<NetworkResult<T, E>>> {
    override fun responseType(): Type = resultType

    override fun adapt(call: Call<T>): Call<NetworkResult<T, E>> = NetworkResultCall(call, errorBodyConverter)
}
