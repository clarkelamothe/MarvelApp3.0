package com.example.marvelapp30.feature_character.data.remote

class CharacterService(
    private val characterApi: CharacterApi
) {
    suspend fun getRemoteCharacters(
        limit: Int, offset: Int
    ) = characterApi.getCharacters(limit, offset)
}