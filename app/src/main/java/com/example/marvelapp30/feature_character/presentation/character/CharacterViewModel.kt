package com.example.marvelapp30.feature_character.presentation.character

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.feature_character.domain.usecase.GetCharactersUseCase
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiIntent
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiIntent.FetchCharacters
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiIntent.OnCharacterPressed
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiIntent.OnListError
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiState
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiState.Error
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiState.Loading
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiState.NavigateToDetails
import com.example.marvelapp30.feature_character.presentation.character.model.CharacterUiState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(
    val charactersUseCase: GetCharactersUseCase
) : BaseViewModel<CharacterUiIntent>() {
    private val _state = MutableStateFlow<CharacterUiState>(CharacterUiState.Idle)
    val state = _state.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is FetchCharacters -> {
                        getCharacters()
                    }

                    is OnCharacterPressed -> {
                        _state.update {
                            NavigateToDetails(intent.character)
                        }
                    }

                    is OnListError -> {
                        _state.update {
                            Error(intent.message)
                        }
                    }

                    CharacterUiIntent.Retry -> {
                        _state.update {
                            CharacterUiState.Refreshed
                        }
                    }
                }
            }
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _state.update { Loading }

            charactersUseCase()
                .catch { throwable ->
                    _state.update { Error(throwable.message ?: "") }
                }
                .cachedIn(this).collectLatest { data ->
                    _state.update { Success(data) }
                }
        }
    }
}