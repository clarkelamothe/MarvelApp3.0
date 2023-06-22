package com.example.marvelapp30.di

import android.app.Application
import com.example.marvelapp30.di.modules.dbModule
import com.example.marvelapp30.di.modules.repositoryModule
import com.example.marvelapp30.di.modules.retrofitModule
import com.example.marvelapp30.di.modules.serviceModule
import com.example.marvelapp30.di.modules.useCaseModule
import com.example.marvelapp30.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarvelApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MarvelApp)

            modules(
                dbModule,
                retrofitModule,
                serviceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}