package com.example.marvelapp30.core.data.model

data class MarvelResponse<T>(
    var code: Int,
    var status: String,
    var data: T
)