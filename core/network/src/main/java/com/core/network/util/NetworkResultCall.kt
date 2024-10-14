package com.core.network.util

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response

class NetworkResultCall<T : Any, E : Any>(
    private val proxy: Call<T>,
    private val errorConverter: Converter<ResponseBody, E>,
) : Call<NetworkResult<T, E>> {

    override fun enqueue(callback: Callback<NetworkResult<T, E>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()
                if (response.isSuccessful && body != null) {
                    callback.onResponse(this@NetworkResultCall, Response.success(NetworkResult.Success(body)))
                } else {
                    val errorBody = when {
                        error == null -> null

                        error.contentLength() == 0L -> null

                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    callback.onResponse(this@NetworkResultCall, Response.success(NetworkResult.Error(code, response.message(), errorBody)))
                }
            }

            override fun onFailure(call: Call<T>, trowable: Throwable) {
                val networkResponse = when (trowable) {
                    is HttpException -> NetworkResult.Exception(trowable, trowable.code())
                    else -> NetworkResult.Exception(trowable)
                }
                callback.onResponse(this@NetworkResultCall, Response.success(networkResponse))
            }
        })
    }

    override fun clone(): Call<NetworkResult<T, E>> = NetworkResultCall(proxy.clone(), errorConverter)

    override fun execute(): Response<NetworkResult<T, E>> = throw NotImplementedError()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun cancel() {
        proxy.cancel()
    }

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()
}
