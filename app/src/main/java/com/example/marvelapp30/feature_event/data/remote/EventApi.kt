package com.example.marvelapp30.feature_event.data.remote

import com.example.marvelapp30.core.data.model.ComicDto
import com.example.marvelapp30.core.data.model.Data
import com.example.marvelapp30.core.data.model.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val EVENT_DEFAULT_ORDER = "startDate"
const val EVENT_DEFAULT_PAGE_LIMIT = 25

interface EventApi {
    @GET("events")
    suspend fun getEvents(
        @Query("limit") limit: Int = EVENT_DEFAULT_PAGE_LIMIT,
        @Query("orderBy") orderBy: String = EVENT_DEFAULT_ORDER,
    ): Response<MarvelResponse<Data<List<EventDto>>>>

    @GET("events/{id}/comics")
    suspend fun getComicsByEventId(
        @Path("id") id: Int
    ): Response<MarvelResponse<Data<List<ComicDto>>>>
}