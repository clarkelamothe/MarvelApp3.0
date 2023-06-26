package com.example.marvelapp30.feature_event.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.feature_event.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading)
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                val response = getEventsUseCase()
                if (response.isSuccessful) {
                    _uiState.value =
                        LatestNewsUiState.Success(getEventsUseCase().body()?.data?.results?.map {
                            UiEvent(
                                title = it.title,
                                imageUrl = "",
                                date = it.start.toInt(),
                                list = emptyList<Any>()
                            )
                        } ?: emptyList())
                }
            } catch (e: Exception) {
                LatestNewsUiState.Error(e.localizedMessage)
            }
        }
    }
}

data class UiEvent(
    val title: String,
    val imageUrl: String,
    val date: Int,
    val list: Any,
    val isExpanded: Boolean = false,
)

sealed class LatestNewsUiState {
    object Loading : LatestNewsUiState()
    data class Success(val characters: List<UiEvent>) : LatestNewsUiState()
    data class Error(val msg: String?) : LatestNewsUiState()
}