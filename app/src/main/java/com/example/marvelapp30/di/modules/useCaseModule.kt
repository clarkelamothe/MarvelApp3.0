package com.example.marvelapp30.di.modules

import com.example.marvelapp30.feature_character.domain.usecase.GetCharactersUseCase
import com.example.marvelapp30.feature_character.domain.usecase.GetComicsUseCase as GetComicsByCharacterId
import com.example.marvelapp30.feature_event.domain.usecase.GetComicsUseCase as GetComicsByEventId
import com.example.marvelapp30.feature_event.domain.usecase.GetEventsUseCase
import com.example.marvelapp30.feature_event.domain.usecase.SetFormattedEventDateUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetCharactersUseCase)
    factoryOf(::GetEventsUseCase)
    factoryOf(::SetFormattedEventDateUseCase)
    factoryOf(::GetComicsByCharacterId)
    factoryOf(::GetComicsByEventId)
}