package com.example.marvelapp30.feature_character.presentation.character.model

import androidx.paging.PagingData
import com.example.marvelapp30.feature_character.domain.model.Character

sealed class CharacterUiState {
    data object Idle : CharacterUiState()
    data object Loading : CharacterUiState()
    data class Success(val pagedData: PagingData<Character>) : CharacterUiState()
    data class Error(val throwable: Throwable) : CharacterUiState()
    data class NavigateToDetails(val character: Character): CharacterUiState()
}
