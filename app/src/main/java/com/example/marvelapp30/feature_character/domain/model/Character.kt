package com.example.marvelapp30.feature_character.domain.model

import java.io.Serializable

data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String
) : Serializable