package com.example.marvelapp30.feature_event.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.feature_event.domain.usecase.GetComicsUseCase
import com.example.marvelapp30.feature_event.domain.usecase.GetEventsUseCase
import com.example.marvelapp30.feature_event.domain.usecase.SetFormattedEventDateUseCase
import com.example.marvelapp30.utils.toUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val setFormattedEventDateUseCase: SetFormattedEventDateUseCase,
    private val getComicsUseCase: GetComicsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading)
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    fun getData() {
        viewModelScope.launch {
            try {
                val response = getEventsUseCase()

                if (!response.isSuccessful) {
                    _uiState.value = LatestNewsUiState.Error(response.message())
                }

                val events = response.body()?.data?.results ?: emptyList()
                _uiState.value = LatestNewsUiState.Success(events.map {
                    EventData(
                        id = it.id,
                        title = it.title,
                        imageUrl = it.thumbnail.toUrl(),
                        date = setFormattedEventDateUseCase(it.start),
                        list = emptyList<Any>()
                    )
                })

            } catch (e: Exception) {
                _uiState.value = LatestNewsUiState.Error(e.localizedMessage)
            }
        }
    }

    fun getComics(id: Int) {
        viewModelScope.launch { 
            try {
                val result = getComicsUseCase(id)

                Log.d("Comics by Event", "getComics: ${result.body()?.data?.results}")

            } catch (e: Exception) {
                _uiState.value = LatestNewsUiState.Error(e.localizedMessage)
            }
            
        }
    }
}

data class EventData(
    val id: Int,
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