package com.example.marvelapp30.feature_character.presentation.model

import androidx.paging.PagingData
import com.example.marvelapp30.feature_character.domain.model.Character

sealed class CharacterUiEvent {
    object Loading : CharacterUiEvent()
    data class Success(val characters: PagingData<Character>) : CharacterUiEvent()
    data class Error(val message: String?) : CharacterUiEvent()
}