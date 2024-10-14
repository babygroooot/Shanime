package com.core.datastore.usersetting

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.core.datastore.UserSettingOuterClass.UserSetting
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserSettingSerializer @Inject constructor() : Serializer<UserSetting> {

    override val defaultValue: UserSetting
        get() = UserSetting.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserSetting {
        try {
            return UserSetting.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserSetting, output: OutputStream) {
        t.writeTo(output)
    }
}
