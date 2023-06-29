package com.example.marvelapp30.feature_character.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "characters"
)
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String
) : Serializable