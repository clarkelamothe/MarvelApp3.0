package com.example.marvelapp30.feature_event.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.apiModel.ApiResult
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.feature_event.domain.model.Event
import com.example.marvelapp30.feature_event.domain.usecase.GetComicsUseCase
import com.example.marvelapp30.feature_event.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val getComicsUseCase: GetComicsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading)
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    private val _comics = MutableStateFlow(emptyList<Comic>())
    val comics: StateFlow<List<Comic>> = _comics

    fun getData() {
        viewModelScope.launch {
            getEventsUseCase()
                .catch {
                    _uiState.value = LatestNewsUiState.Error(it.message)
                }
                .collectLatest { result ->
                    when (result) {
                        is ApiResult.Success<*> -> _uiState.value =
                            LatestNewsUiState.Success(result.data as List<Event>)

                        is ApiResult.Error -> _uiState.value = LatestNewsUiState.Error(
                            result.exception.message
                        )
                    }
                }
        }

    }

    fun getComics(id: Int) {
        viewModelScope.launch {
            getComicsUseCase(id)
                .catch {
                    _uiState.value = LatestNewsUiState.Error(it.message)
                }
                .collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> LatestNewsUiState.Error(result.exception.message)
                        is ApiResult.Success<*> -> _comics.value = (result.data as List<Comic>)
                    }
                }
        }
    }
}

sealed class LatestNewsUiState {
    object Loading : LatestNewsUiState()
    data class Success(val events: List<Event>) : LatestNewsUiState()
    data class Error(val msg: String?) : LatestNewsUiState()
}