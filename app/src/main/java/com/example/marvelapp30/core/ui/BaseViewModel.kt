package com.example.marvelapp30.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiIntent> : ViewModel() {
    private val _intent = Channel<UiIntent>()
    val intent = _intent.receiveAsFlow()

    fun sendEvent(vararg intents: UiIntent) {
        viewModelScope.launch {
            intents.forEach {
                _intent.send(it)
            }
        }
    }
}
