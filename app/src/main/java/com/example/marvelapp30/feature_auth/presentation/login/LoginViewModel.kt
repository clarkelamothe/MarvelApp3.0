package com.example.marvelapp30.feature_auth.presentation.login

import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.core.utils.isEmailValid
import com.example.marvelapp30.core.utils.isPasswordValid
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiEvent

class LoginViewModel : BaseViewModel<AuthUiEvent>() {
    fun checkEntries(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            when {
                !email.trim().isEmailValid() && email.trim().isNotBlank() -> {
                    sendEvent(AuthUiEvent.EmailError, AuthUiEvent.FormValid(false))
                }

                !password.trim().isPasswordValid() && password.trim().isNotBlank() -> {
                    sendEvent(AuthUiEvent.PasswordError, AuthUiEvent.FormValid(false))
                }

                else -> {
                    sendEvent(AuthUiEvent.FormValid(true))
                }
            }
        }
    }

    fun loginPressed(email: String, password: String) {
        sendEvent(AuthUiEvent.OnSubmit("", email, password))
    }
}