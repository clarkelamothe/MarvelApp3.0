package com.example.marvelapp30.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.marvelapp30.apiModel.DateDto
import com.example.marvelapp30.apiModel.Thumbnail
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

const val DATE_TIME_PATTERN = "d 'de' MMMM uuuu"
const val LANGUAGE_CODE = "es"
const val COUNTRY_CODE = "ES"
const val ON_SALE_DATE = "onsaleDate"

fun Thumbnail.toUrl() =
    "$path/standard_large.$extension".replace("http", "https")

fun String.toDateTime(): LocalDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
fun String.fixDateString() = this.replace(" ", "T")

fun LocalDateTime.formatted(): String = this.format(
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