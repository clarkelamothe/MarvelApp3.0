package com.example.marvelapp30.feature_event.domain

import com.example.marvelapp30.core.data.model.ApiResult
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.feature_event.domain.model.Event

interface EventRepository {
    suspend fun getEvents(): ApiResult<List<Event>>
    suspend fun getComics(eventId: Int): ApiResult<List<Comic>>
}