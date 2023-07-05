package com.example.marvelapp30.feature_event.data.remote

class EventService(
    private val eventApi: EventApi
) {
    suspend fun getRemoteEvents() = eventApi.getEvents()

    suspend fun getComicsByEventId(id: Int) = eventApi.getComicsByEventId(id)
}