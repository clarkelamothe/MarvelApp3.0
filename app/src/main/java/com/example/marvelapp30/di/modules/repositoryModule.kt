package com.example.marvelapp30.di.modules

import com.example.marvelapp30.feature_character.data.CharacterRepositoryImpl
import com.example.marvelapp30.feature_character.domain.CharacterRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::CharacterRepositoryImpl) { bind<CharacterRepository>() }
}