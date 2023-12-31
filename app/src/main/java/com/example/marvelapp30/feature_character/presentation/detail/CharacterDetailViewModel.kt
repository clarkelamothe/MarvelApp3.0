package com.example.marvelapp30.feature_character.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.core.data.model.ApiResult.Error
import com.example.marvelapp30.core.data.model.ApiResult.Success
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.feature_character.domain.usecase.GetComicsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val comicsUseCase: GetComicsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ComicState>(ComicState.Loading)
    val uiState: StateFlow<ComicState> = _uiState

    fun getComics(characterId: Int?) {
        viewModelScope.launch {
            characterId?.let { id ->
                comicsUseCase(id)
                    .catch {
                        _uiState.value = ComicState.Error(
                            it.message
                        )
                    }
                    .collectLatest {
                        when (it) {
                            is Success<*> -> _uiState.value =
                                ComicState.Success(it.data as List<Comic>)

                            is Error -> _uiState.value = ComicState.Error(
                                it.exception.message
                            )
                        }
                    }
            } ?: ComicState.Error(msg = "Error in getting list of comics for specified character.")
        }
    }
}

sealed class ComicState {
    data class Success(val comics: List<Comic>) : ComicState()
    data class Error(val msg: String?) : ComicState()
    object Loading : ComicState()
}