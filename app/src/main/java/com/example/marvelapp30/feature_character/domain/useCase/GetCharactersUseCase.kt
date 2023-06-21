package com.example.marvelapp30.feature_character.domain.useCase

import com.example.marvelapp30.feature_character.domain.CharacterRepository

class GetCharactersUseCase(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke() = characterRepository.getCharacters()

}