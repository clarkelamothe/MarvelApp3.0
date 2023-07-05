package com.example.marvelapp30.feature_event.data.remote

import com.example.marvelapp30.apiModel.ComicDto
import com.example.marvelapp30.apiModel.Data
import com.example.marvelapp30.apiModel.MarvelResponse
import com.example.marvelapp30.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @GET("events")
    suspend fun getEvents(
        @Query("limit") limit: Int = Constants.EVENT_DEFAULT_PAGE_LIMIT,
        @Query("orderBy") orderBy: String = Constants.EVENT_DEFAULT_ORDER,
    ): Response<MarvelResponse<Data<List<EventDto>>>>

    @GET("events/{id}/comics")
    suspend fun getComicsByEventId(
        @Path("id") id: Int
    ): Response<MarvelResponse<Data<List<ComicDto>>>>
}