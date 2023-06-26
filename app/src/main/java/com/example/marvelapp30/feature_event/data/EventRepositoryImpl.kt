package com.example.marvelapp30.feature_event.data

import com.example.marvelapp30.feature_event.data.remote.EventService
import com.example.marvelapp30.feature_event.domain.EventRepository

class EventRepositoryImpl(
    private val service: EventService
) : EventRepository {
    override suspend fun getEvents() = service.getRemoteEvents()
}