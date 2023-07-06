package com.example.marvelapp30.feature_event.domain.model

import com.example.marvelapp30.feature_character.domain.model.Comic

data class Event(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val date: String,
    var isExpanded: Boolean = false,
    var comics: List<Comic> = emptyList()
)