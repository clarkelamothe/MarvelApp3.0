package com.example.marvelapp30.utils

import com.example.marvelapp30.apiModel.Thumbnail

fun Thumbnail.toUrl() =
    "$path/standard_large.$extension".replace("http", "https")