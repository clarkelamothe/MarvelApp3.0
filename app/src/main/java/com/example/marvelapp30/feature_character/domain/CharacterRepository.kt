package com.example.marvelapp30.feature_character.domain

interface CharacterRepository {

    suspend fun getCharacters()
}