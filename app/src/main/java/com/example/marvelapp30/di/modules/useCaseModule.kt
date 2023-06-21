package com.example.marvelapp30.di.modules

import com.example.marvelapp30.feature_character.domain.useCase.GetCharactersUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetCharactersUseCase)

}