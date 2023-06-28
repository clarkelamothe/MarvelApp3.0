package com.example.marvelapp30.feature_comic.data.remote

data class ComicDto(
    val id: Int,
    val title: String,
    val dates: List<DateDto>,
)