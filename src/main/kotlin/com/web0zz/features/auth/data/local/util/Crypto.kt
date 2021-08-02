package com.web0zz.features.auth.data.local.util

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun String.hash(): String {
    val hmac = Mac.getInstance(KeyProvider.ALGORITHM)
    hmac.init(KeyProvider.hmacKey)
    return hex(hmac.doFinal(this.toByteArray(Charsets.UTF_8)))
}

object KeyProvider {
    lateinit var hmacKey: SecretKeySpec
        private set

    fun initialize(secret: String) {
        if (!this::hmacKey.isInitialized) {
            hmacKey = SecretKeySpec(secret.toByteArray(), ALGORITHM)
        }
    }

    const val ALGORITHM = "HmacSHA256"
}
