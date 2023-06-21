package com.example.marvelapp30.feature_character.data.remote

import com.example.marvelapp30.apiModel.Thumbnail

data class CharacterDto(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
    val description: String,
)