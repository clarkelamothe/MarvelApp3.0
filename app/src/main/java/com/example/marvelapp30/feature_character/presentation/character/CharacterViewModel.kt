package com.example.marvelapp30.feature_character.presentation.character

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.feature_character.domain.usecase.GetCharactersUseCase
import com.example.marvelapp30.feature_character.presentation.model.CharacterUiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterViewModel(
    val charactersUseCase: GetCharactersUseCase
) : BaseViewModel<CharacterUiEvent>() {

    init {
        sendEvent(CharacterUiEvent.Loading)
    }

    fun getCharacters() {
        viewModelScope.launch {
            charactersUseCase().cachedIn(this).collectLatest {
                sendEvent(CharacterUiEvent.Success(it))
            }
        }
    }

    fun sendError(message: String?) {
        sendEvent(CharacterUiEvent.Error(message))
    }
}