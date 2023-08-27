package com.example.marvelapp30.feature_character.domain

import androidx.paging.Pager
import com.example.marvelapp30.core.data.model.ApiResult
import com.example.marvelapp30.feature_character.domain.model.Character
import com.example.marvelapp30.feature_character.domain.model.Comic

interface CharacterRepository {

    fun getCharacters(): Pager<Int, Character>

    suspend fun getComics(characterId: Int): ApiResult<List<Comic>>
}