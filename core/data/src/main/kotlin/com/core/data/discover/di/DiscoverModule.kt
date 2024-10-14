package com.core.data.discover.di

import com.core.data.discover.DiscoverRemoteDataSource
import com.core.data.discover.DiscoverRepository
import com.core.data.discover.implementation.DiscoverRemoteDataSourceImpl
import com.core.data.discover.implementation.DiscoverRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DiscoverModule {

    @Binds
    internal abstract fun bindsDiscoverRemoteDataSource(
        discoverRemoteDataSourceImpl: DiscoverRemoteDataSourceImpl,
    ): DiscoverRemoteDataSource

    @Binds
    internal abstract fun bindsDiscoverRepository(
        discoverRepositoryImpl: DiscoverRepositoryImpl,
    ): DiscoverRepository
}
