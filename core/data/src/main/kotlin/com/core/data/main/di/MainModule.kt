package com.core.data.main.di

import com.core.data.main.MainLocalDataSource
import com.core.data.main.MainRepository
import com.core.data.main.implementation.MainLocalDataSourceImpl
import com.core.data.main.implementation.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MainModule {

    @Binds
    internal abstract fun bindsMainLocalDataSource(
        mainLocalDataSourceImpl: MainLocalDataSourceImpl,
    ): MainLocalDataSource

    @Binds
    internal abstract fun bindsMainRepository(
        mainRepositoryImpl: MainRepositoryImpl,
    ): MainRepository
}
