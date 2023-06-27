package com.example.marvelapp30.feature_event.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.feature_event.domain.usecase.GetEventsUseCase
import com.example.marvelapp30.utils.toUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading)
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val response = getEventsUseCase()

            try {
                if (response.isSuccessful) {
                    val events = response.body()?.data?.results ?: emptyList()
                    _uiState.value = LatestNewsUiState.Success(events.map {
                        EventData(
                            title = it.title,
                            imageUrl = it.thumbnail.toUrl(),
                            date = it.start ?: "No date specified.",
                            list = emptyList<Any>()
                        )
                    })
                } else _uiState.value = LatestNewsUiState.Error(response.message())

            } catch (e: Exception) {
                LatestNewsUiState.Error(e.localizedMessage)
            }
        }
    }
}

data class EventData(
    val title: String,
    val imageUrl: String,
    val date: String,
    val list: Any,
    val isExpanded: Boolean = false,
)

sealed class LatestNewsUiState {
    object Loading : LatestNewsUiState()
    data class Success(val characters: List<EventData>) : LatestNewsUiState()
    data class Error(val msg: String?) : LatestNewsUiState()
}