package com.example.marvelapp30.utils

import com.example.marvelapp30.apiModel.Thumbnail
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Thumbnail.toUrl() =
    "$path/standard_large.$extension".replace("http", "https")


fun String.toDateTime(): LocalDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
fun String.fixDateString() = this.replace(" ", "T")

fun LocalDateTime.formatted(): String = this.format(
    DateTimeFormatter.ofPattern(
        Constants.DATE_TIME_PATTERN, Locale(Constants.LANGUAGE_CODE, Constants.COUNTRY_CODE)
    )
)

fun String?.toEventDateFormatted(): String = this?.fixDateString()?.toDateTime()?.formatted() ?: ""