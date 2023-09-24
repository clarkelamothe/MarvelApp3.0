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
) : BaseViewModel<LatestNewsUiState>() {

    private val _events = MutableStateFlow(emptyList<Event>())
//    val events: StateFlow<List<Event>> = _events

    init {
        sendEvent(LatestNewsUiState.Loading)
    }

    fun getData() {
        viewModelScope.launch {
            getEventsUseCase()
                .catch {
                    sendEvent(LatestNewsUiState.Error(it.message))
                }
                .collectLatest { result ->
                    when (result) {
                        is ApiResult.Success<*> -> {
                            _events.value = result.data as List<Event>
                            sendEvent(LatestNewsUiState.Success(_events.value))
                        }

                        is ApiResult.Error -> sendEvent(
                            LatestNewsUiState.Error(
                                result.exception.message
                            )
                        )
                    }
                }
        }

    }

    fun getComics(pos: Int, id: Int) {
        viewModelScope.launch {
            getComicsUseCase(id)
                .catch {
                    sendEvent(LatestNewsUiState.Error(it.message))
                }
                .collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> sendEvent(LatestNewsUiState.Error(result.exception.message))
                        is ApiResult.Success<*> -> {
                            _events.value.find {
                                it.id == id
                            }?.let { event ->
                                event.comics = result.data as List<Comic>
                            }

                            sendEvent(LatestNewsUiState.EventExpanded(pos, _events.value))
                        }
                    }
                }
        }
    }
}

sealed class LatestNewsUiState {
    object Loading : LatestNewsUiState()
    data class Success(val events: List<Event>) : LatestNewsUiState()
    data class Error(val msg: String?) : LatestNewsUiState()
    data class EventExpanded(val pos: Int, val events: List<Event>) : LatestNewsUiState()
}