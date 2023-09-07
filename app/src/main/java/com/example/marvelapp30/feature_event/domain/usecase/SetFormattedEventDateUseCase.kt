package com.example.marvelapp30.feature_event.domain.usecase

import com.example.marvelapp30.core.utils.toEventDateFormatted

class SetFormattedEventDateUseCase {

    operator fun invoke(date: String) = date.toEventDateFormatted()
}