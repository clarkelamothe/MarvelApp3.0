package com.example.marvelapp30.feature_event.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.marvelapp30.core.data.model.ApiResult
import com.example.marvelapp30.feature_event.data.remote.EventService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException

class EventRepositoryImplTest {
    private lateinit var repository: EventRepositoryImpl
    private lateinit var service: EventService

    @BeforeEach
    fun setup() {
        service = mockk(relaxed = true)
        repository = EventRepositoryImpl(service)
    }

    @Test
    fun `When event thrown exception, then return apiResult error`() = runBlocking {
        val exception = mockk<HttpException>()

        coEvery { service.getRemoteEvents() } throws exception

        val result = repository.getEvents()

        assertThat(result as ApiResult.Error).isEqualTo(ApiResult.Error(exception))
    }

    @Test
    fun `When comics thrown exception, then return apiResult error`() = runBlocking {
        val exception = mockk<HttpException>()

        coEvery { service.getComicsByEventId(0) } throws exception

        val result = repository.getComics(0)

        assertThat(result as ApiResult.Error).isEqualTo(ApiResult.Error(exception))
    }

//    @Test
//    fun `When event success, then return apiResult success`() = runBlocking {
//        val response = mockk<MarvelResponse<Data<List<EventDto>>>>()
//        coEvery { service.getRemoteEvents() } returns mockk {
//            every { body() } returns response
//            every { code() } returns 202
//        }
//
//        val result = repository.getEvents()
//    }
}