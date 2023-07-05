package com.example.marvelapp30.feature_event.domain.usecase

import com.example.marvelapp30.feature_event.domain.EventRepository
import kotlinx.coroutines.flow.flow

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke() = flow { emit(eventRepository.getEvents()) }
}