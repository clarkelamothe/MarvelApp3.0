package com.example.marvelapp30.feature_character.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelapp30.feature_character.data.local.CharacterEntity
import com.example.marvelapp30.feature_character.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val charactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading)
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    init {
        viewModelScope.launch {
            charactersUseCase()
                .cachedIn(this)
                .collect {
                    _uiState.value = LatestNewsUiState.Success(it)
                }
        }
    }

    sealed class LatestNewsUiState {
        object Loading : LatestNewsUiState()
        data class Success(val characters: PagingData<CharacterEntity>) : LatestNewsUiState()
    }
}