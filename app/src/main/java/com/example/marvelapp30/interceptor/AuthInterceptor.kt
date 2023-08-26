package com.example.marvelapp30.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest

const val API_KEY = "apikey"
const val HASH = "hash"
const val TIMESTAMP = "ts"
const val TIMESTAMP_VALUE = 1

class AuthInterceptor(
    private val privateKey: String,
    private val publicKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter(
                API_KEY,
                publicKey
            )
            .addQueryParameter(TIMESTAMP, TIMESTAMP_VALUE.toString())
            .addQueryParameter(
                HASH,
                generateHash(privateKey, publicKey)
            )
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

private fun generateHash(
    privateKey: String,
    publicKey: String
): String = MessageDigest.getInstance("MD5")
    .digest(("${TIMESTAMP_VALUE}${privateKey}${publicKey}").toByteArray())
    .joinToString("") { "%02x".format(it) }