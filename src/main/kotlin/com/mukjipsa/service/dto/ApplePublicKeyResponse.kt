package com.mukjipsa.service.dto

import java.util.*

class ApplePublicKeyResponse(val keys: List<Key>) {
    data class Key(
            var kty: String,
            var kid: String,
            var use: String,
            var alg: String,
            var n: String,
            var e: String
    )

    fun getMatchedKeyBy(kid: String, alg: String): Key? {
        return keys.firstOrNull { it.kid == kid && it.alg == alg }
    }
}