package com.example.marvelapp30.feature_auth.presentation.login

import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent
import com.example.marvelapp30.utils.isEmailValid
import com.example.marvelapp30.utils.isPasswordValid

class LoginViewModel : BaseViewModel<LoginUiEvent>() {
    fun checkEntries(email: String, password: String) {
        when {
            !email.isEmailValid() -> {
                sendEvent(LoginUiEvent.EmailError)
            }

            !password.isPasswordValid() -> {
                sendEvent(LoginUiEvent.PasswordError)
            }

            else -> {
                sendEvent(LoginUiEvent.FormValid)
            }
        }
    }

    fun loginPressed(email: String, password: String) {
        sendEvent(LoginUiEvent.LoginPressed(email, password))
    }
}