package com.example.marvelapp30.feature_event.domain.usecase

import com.example.marvelapp30.feature_event.domain.EventRepository

class GetComicsUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(id: Int) = eventRepository.getComics(id)
}