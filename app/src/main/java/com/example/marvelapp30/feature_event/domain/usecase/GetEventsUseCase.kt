package com.example.marvelapp30.feature_event.domain.usecase

import com.example.marvelapp30.feature_event.domain.EventRepository

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke() = eventRepository.getEvents()
}