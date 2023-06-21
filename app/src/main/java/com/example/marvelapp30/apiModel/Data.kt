package com.example.marvelapp30.apiModel

data class Data<U>(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: U,
    val total: Int
)