package com.example.marvelapp30.feature_auth.presentation.login

import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.core.utils.isEmailValid
import com.example.marvelapp30.core.utils.isPasswordValid
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent

class LoginViewModel : BaseViewModel<LoginUiEvent>() {
    fun checkEntries(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            when {
                !email.trim().isEmailValid() && email.isNotBlank() -> {
                    sendEvent(LoginUiEvent.EmailError)
                }

                !password.trim().isPasswordValid() && password.isNotBlank() -> {
                    sendEvent(LoginUiEvent.PasswordError)
                }

                else -> {
                    sendEvent(LoginUiEvent.FormValid)
                }
            }
        }
    }

    fun loginPressed(email: String, password: String) {
        sendEvent(LoginUiEvent.LoginPressed(email, password))
    }
}