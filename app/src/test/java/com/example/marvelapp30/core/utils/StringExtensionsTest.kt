package com.example.marvelapp30.core.utils

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.example.marvelapp30.core.data.model.DateDto
import com.example.marvelapp30.core.data.model.ThumbnailDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StringExtensionsTest {
    @Test
    fun `given a thumbnailDto then returns a url for thumbnail`() {
        val thumbnailDto = ThumbnailDto(
            "jpg",
            "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
        )

        val expected =
            "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784/standard_large.jpg"

        assertThat(thumbnailDto.toUrl()).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        "1989-12-10 00:00:00, 10 de diciembre 1989",
        "1991-11-10T00:00:00, 10 de noviembre 1991"
    )
    fun `given a string then returns a correct date string`(actual: String?, expected: String) {
        assertThat(actual.toEventDateFormatted()).isEqualTo(expected)
    }

    @Test
    fun `given a null then returns empty string`() {
        val actual: String? = null
        assertThat(actual.toEventDateFormatted()).isEmpty()
    }

    @Test
    fun `given a list of DateDto then return date of onSaleDate`() {
        val expectedDate = "10 de octubre"
        val list = listOf(
            DateDto("onsaleDate", "10 de octubre"),
            DateDto("purchaseDate", "10 de octubre"),
            DateDto("", "10 de noviembre"),
            DateDto("onsaleDate", "10 de diciembre")
        )

        assertThat(list.getSaleDate()).isEqualTo(expectedDate)
    }
}