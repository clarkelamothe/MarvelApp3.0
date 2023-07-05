package com.example.marvelapp30.feature_event.domain.usecase

import com.example.marvelapp30.feature_event.domain.EventRepository
import kotlinx.coroutines.flow.flow

class GetComicsUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(id: Int) = flow { emit(eventRepository.getComics(id)) }
}