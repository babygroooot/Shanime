package com.core.data.seasonal.di

import com.core.data.seasonal.SeasonalRemoteDataSource
import com.core.data.seasonal.SeasonalRepository
import com.core.data.seasonal.implementation.SeasonalRemoteDataSourceImpl
import com.core.data.seasonal.implementation.SeasonalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SeasonalModule {

    @Binds
    internal abstract fun bindsSeasonalRemoteDataSource(
        seasonalRemoteDataSourceImpl: SeasonalRemoteDataSourceImpl,
    ): SeasonalRemoteDataSource

    @Binds
    internal abstract fun bindsSeasonalRepository(
        seasonalRepositoryImpl: SeasonalRepositoryImpl,
    ): SeasonalRepository
}
