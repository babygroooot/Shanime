package com.core.network.di

import com.core.datastore.DataStoreManager
import com.core.network.BuildConfig
import com.core.network.util.NetworkResultCallAdapterFactory
import com.core.network.util.authenticator.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideBaseURL(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun okHttpCallFactory(
        loggingInterceptor: HttpLoggingInterceptor,
        dataStoreManager: DataStoreManager,
        tokenAuthenticator: TokenAuthenticator,
    ): Call.Factory {
        val okHttpClientBuilder = OkHttpClient.Builder()

        okHttpClientBuilder.callTimeout(20, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(20, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(20, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(20, TimeUnit.SECONDS)
        okHttpClientBuilder.authenticator(tokenAuthenticator)

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.interceptors().add(loggingInterceptor)
        }

        okHttpClientBuilder.addInterceptor { chain ->
            val request = chain.request()
            val token = runBlocking { dataStoreManager.getToken() }
            val builder = request.newBuilder()
            if (token.isNullOrBlank().not()) {
                builder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(builder.build())
        }

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        val contentType = "application/json; charset=UTF-8".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun provideAdapterFactory(): CallAdapter.Factory = NetworkResultCallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
        baseUrl: String,
        converterFactory: Converter.Factory,
        adapterFactory: CallAdapter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .callFactory {
            okhttpCallFactory.get().newCall(it)
        }
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(adapterFactory)
        .build()
}
