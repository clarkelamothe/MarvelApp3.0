package com.example.marvelapp30.feature_event.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SetFormattedEventDateUseCaseTest {
    private lateinit var setFormattedEventDateUseCase: SetFormattedEventDateUseCase

    @BeforeEach
    fun setup() {
        setFormattedEventDateUseCase = SetFormattedEventDateUseCase()
    }

    @Test
    fun `Test a date is formatted correctly`() {
        val date = "1989-12-10 00:00:00"
        val formatted = setFormattedEventDateUseCase(date)

        assertThat(formatted).isEqualTo("10 de diciembre 1989")
    }
}