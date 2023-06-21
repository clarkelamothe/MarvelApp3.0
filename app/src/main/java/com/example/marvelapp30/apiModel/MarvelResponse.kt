package com.example.marvelapp30.apiModel

data class MarvelResponse<T>(
    var code: Int,
    var status: String,
    var data: T
)