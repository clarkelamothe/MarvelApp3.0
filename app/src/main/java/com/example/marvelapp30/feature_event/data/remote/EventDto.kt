package com.example.marvelapp30.featur_event.data.remote

import com.example.marvelapp30.apiModel.Thumbnail

data class EventDto(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail,
    val startYear: String
)