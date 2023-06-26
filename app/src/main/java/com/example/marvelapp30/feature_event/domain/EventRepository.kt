package com.example.marvelapp30.feature_event.domain

import com.example.marvelapp30.apiModel.Data
import com.example.marvelapp30.apiModel.MarvelResponse
import com.example.marvelapp30.feature_event.data.remote.EventDto
import retrofit2.Response

interface EventRepository {
    suspend fun getEvents(): Response<MarvelResponse<Data<List<EventDto>>>>
}