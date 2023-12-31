package com.example.marvelapp30.feature_event.data

import com.example.marvelapp30.core.data.model.ApiResult
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.feature_event.data.remote.EventService
import com.example.marvelapp30.feature_event.domain.EventRepository
import com.example.marvelapp30.feature_event.domain.model.Event
import com.example.marvelapp30.core.utils.fixComicYear
import com.example.marvelapp30.core.utils.getSaleDate
import com.example.marvelapp30.core.utils.toEventDateFormatted
import com.example.marvelapp30.core.utils.toUrl

class EventRepositoryImpl(
    private val service: EventService
) : EventRepository {
    override suspend fun getEvents(): ApiResult<List<Event>> {
        return try {
            val events = service.getRemoteEvents()
            ApiResult.Success(
                events.body()?.data?.results?.map {
                    Event(
                        id = it.id,
                        title = it.title,
                        imageUrl = it.thumbnail.toUrl(),
                        date = it.start.toEventDateFormatted()
                    )
                }
            )
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }

    override suspend fun getComics(eventId: Int): ApiResult<List<Comic>> {
        return try {
            val comics = service.getComicsByEventId(eventId)
            ApiResult.Success(
                comics.body()?.data?.results?.map {
                    Comic(
                        id = it.id,
                        title = it.title,
                        year = it.dates.getSaleDate().fixComicYear()
                    )
                }
            )
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}