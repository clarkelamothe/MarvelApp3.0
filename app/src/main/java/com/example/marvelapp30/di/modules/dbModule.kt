package com.example.marvelapp30.di.modules

import com.example.marvelapp30.db.MarvelAppDb
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        MarvelAppDb.getDatabase(androidApplication())
    }

    single {
        get<MarvelAppDb>().characterDao()
    }
}