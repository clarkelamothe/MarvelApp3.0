package com.example.marvelapp30.feature_comic.data.remote

import com.example.marvelapp30.apiModel.Data
import com.example.marvelapp30.apiModel.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicApi {
    @GET("events/{id}/comics")
    suspend fun getComicsByEventId(
        @Path("id") id: Int,
        @Query("limit") limit: Int
    ): Response<MarvelResponse<Data<List<ComicDto>>>>

    @GET("characters/{id}/comics")
    suspend fun getComicsByCharacterId(
        @Path("id") id: Int,
        @Query("limit") limit: Int
    ): Response<MarvelResponse<Data<List<ComicDto>>>>
}