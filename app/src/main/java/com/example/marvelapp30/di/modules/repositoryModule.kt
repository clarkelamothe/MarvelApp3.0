package com.example.marvelapp30.di.modules

import com.example.marvelapp30.feature_character.data.CharacterRepositoryImpl
import com.example.marvelapp30.feature_character.data.paging.CharacterRemoteMediator
import com.example.marvelapp30.feature_character.domain.CharacterRepository
import com.example.marvelapp30.feature_comic.data.ComicRepositoryImpl
import com.example.marvelapp30.feature_comic.domain.ComicRepository
import com.example.marvelapp30.feature_event.data.EventRepositoryImpl
import com.example.marvelapp30.feature_event.domain.EventRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::CharacterRepositoryImpl) { bind<CharacterRepository>() }
    factory { CharacterRemoteMediator(get(), get()) }

    factoryOf(::EventRepositoryImpl) { bind<EventRepository>() }

    factoryOf(::ComicRepositoryImpl) { bind<ComicRepository>() }
}