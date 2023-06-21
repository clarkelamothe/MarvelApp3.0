package com.example.marvelapp30.feature_character.data.local.mappers

import com.example.marvelapp30.feature_character.data.local.CharacterEntity
import com.example.marvelapp30.feature_character.data.remote.CharacterDto
import com.example.marvelapp30.utils.toUrl

fun CharacterDto.toEntity() = CharacterEntity(
    id = id,
    name = name,
    imageUrl = thumbnail.toUrl(),
    description = description
)