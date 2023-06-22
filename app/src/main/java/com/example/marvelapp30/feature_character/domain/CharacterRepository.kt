package com.example.marvelapp30.feature_character.domain

import androidx.paging.Pager
import com.example.marvelapp30.feature_character.data.local.CharacterEntity

interface CharacterRepository {

    suspend fun getCharacters(): Pager<Int, CharacterEntity>
}