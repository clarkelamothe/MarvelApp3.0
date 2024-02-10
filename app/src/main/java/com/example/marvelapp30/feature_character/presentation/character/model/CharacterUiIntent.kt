package com.example.marvelapp30.feature_character.presentation.character.model

import com.example.marvelapp30.feature_character.domain.model.Character

sealed class CharacterUiIntent {
    data object FetchCharacters : CharacterUiIntent()
    data class OnCharacterPressed(val character: Character) : CharacterUiIntent()
}
