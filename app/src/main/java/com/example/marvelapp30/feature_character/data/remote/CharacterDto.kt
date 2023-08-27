package com.example.marvelapp30.feature_character.data.remote

import com.example.marvelapp30.core.data.model.ThumbnailDto as Thumbnail

data class CharacterDto(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
    val description: String,
)