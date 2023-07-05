package com.example.marvelapp30.feature_event.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.apiModel.ApiResult
import com.example.marvelapp30.apiModel.ComicDto
import com.example.marvelapp30.feature_event.domain.model.Event
import com.example.marvelapp30.feature_event.domain.usecase.GetComicsUseCase
import com.example.marvelapp30.feature_event.domain.usecase.GetEventsUseCase
import com.example.marvelapp30.feature_event.domain.usecase.SetFormattedEventDateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val setFormattedEventDateUseCase: SetFormattedEventDateUseCase,
    private val getComicsUseCase: GetComicsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading)
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    private val _comics = MutableStateFlow(emptyList<ComicDto>())
    val comics: StateFlow<List<ComicDto>> = _comics

    fun getData() {
        viewModelScope.launch {
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
    }

    fun getComics(id: Int) {
//        viewModelScope.launch {
//            try {
//                val result = getComicsUseCase(id)
//
//                Log.d("Comics by Event", "getComics: ${result.body()?.data?.results}")
//
//                if (!result.isSuccessful) _uiState.value = LatestNewsUiState.Error(result.message())
//
//                val comics = result.body()?.data?.results ?: emptyList()
//
//                _comics.value = comics
//
//
//            } catch (e: Exception) {
//                _uiState.value = LatestNewsUiState.Error(e.localizedMessage)
//            }
//
//        }
    }

    fun setComics(comics: List<ComicDto>) {
        val expected = uiState.value

    }
}

data class EventData(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val date: String,
    var list: Any,
    var isExpanded: Boolean = false,
)

sealed class LatestNewsUiState {
    object Loading : LatestNewsUiState()
    data class Success(val events: List<Event>) : LatestNewsUiState()
    data class Error(val msg: String?) : LatestNewsUiState()
}