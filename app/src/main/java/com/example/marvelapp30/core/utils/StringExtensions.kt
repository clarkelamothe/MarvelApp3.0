package com.example.marvelapp30.core.utils

import android.util.Patterns
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.marvelapp30.core.data.model.DateDto
import com.example.marvelapp30.core.data.model.ThumbnailDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.regex.Pattern

const val DATE_TIME_PATTERN = "d 'de' MMMM uuuu"
const val LANGUAGE_CODE = "es"
const val COUNTRY_CODE = "ES"
const val IMG_ENDPOINT_SIZE = "/standard_large"
const val ON_SALE_DATE = "onsaleDate"
const val REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+\$).{6,}"

fun ThumbnailDto.toUrl() =
    "$path$IMG_ENDPOINT_SIZE.$extension".replace("http", "https")

private fun String.toDateTime(): LocalDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
private fun String.fixDateString() = this.replace(" ", "T")

private fun LocalDateTime.formatted(): String = this.format(
    DateTimeFormatter.ofPattern(
        DATE_TIME_PATTERN, Locale(LANGUAGE_CODE, COUNTRY_CODE)
    )
)

fun String?.toEventDateFormatted(): String = this?.fixDateString()?.toDateTime()?.formatted() ?: ""

fun List<DateDto>.getSaleDate() = this.find {
    it.type == ON_SALE_DATE
}?.date ?: ""

fun String.fixComicYear() = this.substringBeforeLast("-").toDateTime().year

fun String.loadUrl(into: ImageView) =
    Glide.with(into.context).load(this).into(into)

fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPasswordValid() = Pattern.compile(REGEX_PASSWORD).matcher(this).matches()