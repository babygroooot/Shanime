package com.core.datastore

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.core.datastore.usersetting.UserSettingSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    internal fun provideUserSettingDataStore(
        @ApplicationContext context: Context,
        userSettingSerializer: UserSettingSerializer,
    ) = DataStoreFactory.create(
        serializer = userSettingSerializer,
    ) {
        context.dataStoreFile(fileName = "user_setting.pb")
    }
}
