package com.example.marvelapp30.interceptor

import com.example.marvelapp30.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest

class AuthInterceptor(
    private val privateKey: String,
    private val publicKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter(
                Constants.API_KEY,
                publicKey
            )
            .addQueryParameter(Constants.TIMESTAMP, Constants.TIMESTAMP_VALUE.toString())
            .addQueryParameter(
                Constants.HASH,
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
    .digest(("${Constants.TIMESTAMP_VALUE}${privateKey}${publicKey}").toByteArray())
    .joinToString("") { "%02x".format(it) }