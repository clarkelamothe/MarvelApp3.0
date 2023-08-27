package com.example.marvelapp30.feature_event.data.remote

import com.example.marvelapp30.core.data.model.ThumbnailDto as Thumbnail

data class EventDto(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail,
    val start: String?
)