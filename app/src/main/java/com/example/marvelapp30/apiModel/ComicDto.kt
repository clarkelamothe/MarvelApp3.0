package com.example.marvelapp30.apiModel

data class ComicDto(
    val id: Int,
    val title: String,
    val dates: List<DateDto>
)
