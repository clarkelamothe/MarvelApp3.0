package com.example.marvelapp30.di.modules

import com.example.marvelapp30.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single(named(Qualifier.BASE_URL)) { BuildConfig.BASE_URL }
    single(named(Qualifier.PUBLIC_KEY)) { BuildConfig.PUBLIC_KEY }
    single(named(Qualifier.PRIVATE_KEY)) { BuildConfig.PRIVATE_KEY }

    single {
        Retrofit.Builder().baseUrl(get<String>(named(Qualifier.BASE_URL))).client(get())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single {
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BASIC
            )
        ).build()
    }
}

enum class Qualifier {
    BASE_URL,
    PUBLIC_KEY,
    PRIVATE_KEY
}