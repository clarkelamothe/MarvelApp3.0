package com.example.marvelapp30.feature_character.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.marvelapp30.feature_character.domain.usecase.GetCharactersUseCase

class CharacterViewModel(
    charactersUseCase: GetCharactersUseCase
) : ViewModel() {

    val characters = charactersUseCase().cachedIn(viewModelScope)

}