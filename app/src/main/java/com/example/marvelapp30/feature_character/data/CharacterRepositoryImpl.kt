package com.example.marvelapp30.feature_character.data

import com.example.marvelapp30.feature_character.data.remote.CharacterService
import com.example.marvelapp30.feature_character.domain.CharacterRepository

class CharacterRepositoryImpl(
    private val characterService: CharacterService
) : CharacterRepository {
    override suspend fun getCharacters() {
        characterService.getRemoteCharacters(20, 0)
    }
}