package com.example.marvelapp30.feature_character.domain.usecase

import com.example.marvelapp30.feature_character.domain.CharacterRepository
import kotlinx.coroutines.flow.flow

class GetComicsUseCase(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: Int) = flow {
        emit(characterRepository.getComics(characterId))
    }
}