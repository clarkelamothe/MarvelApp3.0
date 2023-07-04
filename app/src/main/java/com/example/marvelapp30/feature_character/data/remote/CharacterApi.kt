package com.example.marvelapp30.feature_character.data.remote

import com.example.marvelapp30.apiModel.ComicDto
import com.example.marvelapp30.apiModel.Data
import com.example.marvelapp30.apiModel.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<MarvelResponse<Data<List<CharacterDto>>>>

    @GET("characters/{id}/comics")
    suspend fun getComicsByCharacterId(
        @Path("id") id: Int
    ): Response<MarvelResponse<Data<List<ComicDto>>>>
}