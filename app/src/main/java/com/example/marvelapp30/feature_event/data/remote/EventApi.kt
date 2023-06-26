package com.example.marvelapp30.feature_event.data.remote

import com.example.marvelapp30.apiModel.Data
import com.example.marvelapp30.apiModel.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {
    @GET("events")
    suspend fun getEvents(
        @Query("limit") limit: Int = 25,
        @Query("orderBy") orderBy: String = "startDate",
    ): Response<MarvelResponse<Data<List<EventDto>>>>
}