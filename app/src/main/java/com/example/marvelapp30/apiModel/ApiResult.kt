package com.example.marvelapp30.apiModel

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<Nothing>()
    data class Error<T>(val exception: Exception) : ApiResult<T>()
}