package com.example.marvelapp30.feature_character.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.feature_character.domain.useCase.GetCharactersUseCase
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val charactersUseCase: GetCharactersUseCase
) : ViewModel() {



    init {
        viewModelScope.launch {
            charactersUseCase()
        }
    }
}