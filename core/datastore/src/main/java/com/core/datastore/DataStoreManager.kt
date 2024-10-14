package com.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "pref_datastore")

class DataStoreManager @Inject constructor(
    @ApplicationContext context: Context,
    private val cipherWrapper: CipherWrapper,
) {
    private val dataStore = context.dataStore
    private val json = Json {
        encodeDefaults = true
        isLenient = true
    }

    fun getDataStore(): DataStore<Preferences> = dataStore

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun setString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getString(key: String): Flow<String> {
        val prefKey = stringPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey] ?: ""
        }
    }

    suspend fun setEncryptedString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        val encryptedValue = cipherWrapper.encryptData(Json.encodeToString(value))
        dataStore.edit { prefs ->
            prefs[prefKey] = encryptedValue
        }
    }

    suspend fun getEncryptedString(key: String): String {
        val preferences = dataStore.data.first()
        preferences[stringPreferencesKey(key)]?.let { encrypt ->
            val decodedString = cipherWrapper.decryptData(encrypt)
            return json.decodeFromString(decodedString)
        }.run {
            return ""
        }
    }

    suspend fun setBoolean(key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getBoolean(key: String): Flow<Boolean> {
        val prefKey = booleanPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey] ?: false
        }
    }

    suspend fun setInt(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getInt(key: String): Flow<Int> {
        val prefKey = intPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey] ?: -1
        }
    }

    suspend fun setLong(key: String, value: Long) {
        val prefKey = longPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getLong(key: String): Flow<Long> {
        val prefKey = longPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey] ?: -1L
        }
    }

    suspend fun setFloat(key: String, value: Float) {
        val prefKey = floatPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getFloat(key: String): Flow<Float> {
        val prefKey = floatPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey] ?: 0.0f
        }
    }

    suspend fun setDouble(key: String, value: Double) {
        val prefKey = doublePreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getDouble(key: String): Flow<Double> {
        val prefKey = doublePreferencesKey(key)
        return dataStore.data.map {
            it[prefKey] ?: 0.0
        }
    }

    suspend fun saveToken(token: String) {
        val encryptedValue = cipherWrapper.encryptData(Json.encodeToString(token))
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("Token")] = encryptedValue
        }
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        preferences[stringPreferencesKey("Token")]?.let { encrypt ->
            val decodedString = cipherWrapper.decryptData((encrypt))
            return json.decodeFromString(decodedString)
        }.run {
            return ""
        }
    }

    suspend fun removeToken() {
        dataStore.edit {
            it.remove(stringPreferencesKey("Token"))
        }
    }

    suspend fun saveRefreshToken(token: String) {
        val encryptedValue = cipherWrapper.encryptData(Json.encodeToString(token))
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("RefreshToken")] = encryptedValue
        }
    }

    suspend fun getRefreshToken(): String? {
        val preferences = dataStore.data.first()
        preferences[stringPreferencesKey("RefreshToken")]?.let { encrypt ->
            val decodedString = cipherWrapper.decryptData((encrypt))
            return json.decodeFromString(decodedString)
        }.run {
            return ""
        }
    }

    suspend fun removeRefreshToken() {
        dataStore.edit {
            it.remove(stringPreferencesKey("RefreshToken"))
        }
    }

    suspend fun clearPreference() {
        dataStore.edit {
            it.clear()
        }
    }
}
