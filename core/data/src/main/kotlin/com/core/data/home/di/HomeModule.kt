package com.core.data.home.di

import com.core.data.home.HomeRemoteDataSource
import com.core.data.home.HomeRepository
import com.core.data.home.implementation.HomeRemoteDataSourceImpl
import com.core.data.home.implementation.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeModule {

    @Binds
    internal abstract fun bindsHomeRemoteDataSource(
        homeRemoteDataSourceImpl: HomeRemoteDataSourceImpl,
    ): HomeRemoteDataSource

    @Binds
    internal abstract fun bindsHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl,
    ): HomeRepository
}
