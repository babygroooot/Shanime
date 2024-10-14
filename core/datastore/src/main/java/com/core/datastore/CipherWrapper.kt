package com.core.datastore

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class CipherWrapper @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val keyAlias = context.packageName

    companion object {
        private const val IV_SEPARATOR = "]"
    }

    private val provider = "AndroidKeyStore"
    private val cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }
    private val keyStore by lazy {
        KeyStore.getInstance(provider).apply {
            load(null)
        }
    }
    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KEY_ALGORITHM_AES, provider)
    }

    fun encryptData(text: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())

        var result: String
        val iv = cipher.iv
        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
        result = ivString + IV_SEPARATOR
        val bytes = cipher.doFinal(text.toByteArray())
        result += Base64.encodeToString(bytes, Base64.DEFAULT)

        return result
    }

    fun decryptData(data: String): String {
        val split = data.split(IV_SEPARATOR.toRegex())
        if (split.size != 2) return ""
        val ivString = split[0]
        val iv = Base64.decode(ivString, Base64.DEFAULT)
        val encodedString: String = split[1]
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(128, iv))
        val encryptedData = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData)
    }

    private fun isKeyExists(keyStore: KeyStore): Boolean {
        val aliases = keyStore.aliases()
        while (aliases.hasMoreElements()) {
            return (keyAlias == aliases.nextElement())
        }
        return false
    }
    private fun generateSecretKey(): SecretKey = keyGenerator.apply {
        init(
            KeyGenParameterSpec
                .Builder(keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                .setBlockModes(BLOCK_MODE_GCM)
                .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                .build(),
        )
    }.generateKey()

    private fun getSecretKey(): SecretKey = if (isKeyExists(keyStore)) {
        (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
    } else {
        generateSecretKey()
    }
}
