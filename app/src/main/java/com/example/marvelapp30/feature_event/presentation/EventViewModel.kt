package com.example.marvelapp30.feature_event.presentation

import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.core.data.model.ApiResult
import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.feature_event.domain.model.Event
import com.example.marvelapp30.feature_event.domain.usecase.GetComicsUseCase
import com.example.marvelapp30.feature_event.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val getComicsUseCase: GetComicsUseCase
) : BaseViewModel<EventsUiState>() {

    private val _events = MutableStateFlow(emptyList<Event>())

    init {
        sendEvent(EventsUiState.Loading)
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            getEventsUseCase()
                .catch {
                    sendEvent(EventsUiState.Error(it.message))
                }
                .collectLatest { result ->
                    when (result) {
                        is ApiResult.Success<*> -> {
                            _events.value = result.data as List<Event>
                            sendEvent(EventsUiState.Success(_events.value))
                        }

                        is ApiResult.Error -> sendEvent(
                            EventsUiState.Error(
                                result.exception.message
                            )
                        )
                    }
                }
        }

    }

    fun getComics(eventId: Int) {
        viewModelScope.launch {
            sendEvent(EventsUiState.Loading)
            getComicsUseCase(eventId)
                .catch {
                    sendEvent(EventsUiState.Error(it.message))
                }
                .collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> sendEvent(EventsUiState.Error(result.exception.message))
                        is ApiResult.Success<*> -> {
                            updateEventWithComics(eventId, result.data as List<Comic>)
                            sendEvent(EventsUiState.ComicSuccess(getIndex(eventId), result.data))
                        }
                    }
                }
        }
    }

    private fun getIndex(eventId: Int) = _events.value.indexOfFirst {
        it.id == eventId
    }

    private fun updateEventWithComics(eventId: Int, comics: List<Comic>) {
        viewModelScope.launch {
            _events.value.find {
                it.id == eventId
            }?.apply {
                this.isExpanded = !this.isExpanded
                this.comics = comics
            }

        }
    }
}

sealed class EventsUiState {
    object Loading : EventsUiState()
    data class Success(val events: List<Event>, val index: Int? = null) : EventsUiState()
    data class Error(val msg: String?) : EventsUiState()
    data class ComicSuccess(val index: Int, val comics: List<Comic>) : EventsUiState()
}