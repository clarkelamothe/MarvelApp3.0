package com.example.marvelapp30.feature_character.presentation.detail

import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.core.data.model.ApiResult.Error
import com.example.marvelapp30.core.data.model.ApiResult.Success
import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.feature_character.domain.usecase.GetComicsUseCase
import com.example.marvelapp30.feature_character.presentation.detail.ComicState.Loading
import com.example.marvelapp30.feature_character.presentation.detail.ComicUiIntent.FetchComics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val comicsUseCase: GetComicsUseCase
) : BaseViewModel<ComicUiIntent>() {

    private val _state = MutableStateFlow<ComicState>(Loading)
    val state = _state.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collect{
                when (it) {
                    is FetchComics -> {
                        getComics(it.characterId)
                    }
                }
            }
        }
    }

    private fun getComics(characterId: Int?) {
        viewModelScope.launch {
            characterId?.let { id ->
                comicsUseCase(id)
                    .catch { throwable ->
                        _state.update { ComicState.Error(throwable.message) }
                    }
                    .collectLatest { result ->
                        when (result) {
                            is Success<*> -> {
                                _state.update { ComicState.Success(result.data as List<Comic>) }
                            }

                            is Error -> {
                                _state.update { ComicState.Error(result.exception.message) }
                            }
                        }
                    }
            }
                ?: _state.update { ComicState.Error("Error in getting list of comics for specified character.") }
        }
    }
}

sealed class ComicUiIntent {
    data class FetchComics(val characterId: Int?): ComicUiIntent()
}

sealed class ComicState {
    data class Success(val comics: List<Comic>) : ComicState()
    data class Error(val msg: String?) : ComicState()
    data object Loading : ComicState()
}