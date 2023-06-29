package com.example.marvelapp30.di.modules

import com.example.marvelapp30.feature_character.presentation.CharacterViewModel
import com.example.marvelapp30.feature_character_details.presentation.CharacterDetailViewModel
import com.example.marvelapp30.feature_event.presentation.EventViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CharacterViewModel)
    viewModelOf(::EventViewModel)
    viewModelOf(::CharacterDetailViewModel)
}