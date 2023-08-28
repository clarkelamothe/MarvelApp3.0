package com.example.marvelapp30.feature_auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent
import com.example.marvelapp30.utils.isEmailValid
import com.example.marvelapp30.utils.isPasswordValid
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val eventChannel = Channel<LoginUiEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun checkEntries(email: String, password: String) {
        viewModelScope.launch {
            when {
                !email.isEmailValid() -> {
                    eventChannel.send(LoginUiEvent.EmailError)
                }

                !password.isPasswordValid() -> {
                    eventChannel.send(LoginUiEvent.PasswordError)
                }

                else -> {
                    eventChannel.send(LoginUiEvent.FormValid)
                }
            }
        }
    }

    fun loginPressed(email: String, password: String) {
        viewModelScope.launch {
            eventChannel.send(LoginUiEvent.LoginPressed(email, password))
        }
    }
}