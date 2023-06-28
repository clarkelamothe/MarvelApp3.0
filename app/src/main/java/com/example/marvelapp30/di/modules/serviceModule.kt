package com.example.marvelapp30.di.modules

import com.example.marvelapp30.feature_character.data.remote.CharacterApi
import com.example.marvelapp30.feature_character.data.remote.CharacterService
import com.example.marvelapp30.feature_comics.data.remote.ComicApi
import com.example.marvelapp30.feature_comics.data.remote.ComicService
import com.example.marvelapp30.feature_event.data.remote.EventApi
import com.example.marvelapp30.feature_event.data.remote.EventService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    factory {
        CharacterService(
            get<Retrofit>().create(CharacterApi::class.java)
        )
    }

    factory {
        EventService(
            get<Retrofit>().create(EventApi::class.java)
        )
    }

    factory {
        ComicService(
            get<Retrofit>().create(ComicApi::class.java)
        )
    }
}