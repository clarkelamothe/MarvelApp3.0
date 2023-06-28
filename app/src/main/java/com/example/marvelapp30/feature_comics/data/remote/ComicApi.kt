package com.example.marvelapp30.feature_comics.data.remote

import com.example.marvelapp30.apiModel.Data
import com.example.marvelapp30.apiModel.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicApi {
    @GET("comics")
    suspend fun getComics(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<MarvelResponse<Data<List<ComicDto>>>>
}