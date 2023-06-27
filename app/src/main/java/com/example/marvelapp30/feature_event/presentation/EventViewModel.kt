package com.example.marvelapp30.feature_event.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.feature_event.domain.usecase.GetEventsUseCase
import com.example.marvelapp30.utils.toUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading)
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    init {
        Log.d("EventFragment: ", "EventViewModel Init")

    }

    fun getData() {
        viewModelScope.launch {
            val response = getEventsUseCase()

            try {
                if (response.isSuccessful) {
                    val flow = flowOf(
                        response.body()?.data?.results ?: emptyList()
                    ).map { eventDtos ->
                        LatestNewsUiState.Success(eventDtos.map {
                            UiEvent(
                                title = it.title,
                                imageUrl = it.thumbnail.toUrl(),
                                date = "9 de Agosto 2022",
                                list = emptyList<Any>()
                            )
                        })
                    }.stateIn(this)
                    _uiState.value = flow.value
                }
//                _uiState.value = LatestNewsUiState.Error(response.message())

            } catch (e: Exception) {
                LatestNewsUiState.Error(e.localizedMessage)
            }
        }
    }
}

data class UiEvent(
    val title: String,
    val imageUrl: String,
    val date: String,
    val list: Any,
    val isExpanded: Boolean = false,
)

sealed class LatestNewsUiState {
    object Loading : LatestNewsUiState()
    data class Success(val characters: List<UiEvent>) : LatestNewsUiState()
    data class Error(val msg: String?) : LatestNewsUiState()
}