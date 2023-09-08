package com.example.marvelapp30.feature_auth.presentation.signup

import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.core.utils.isEmailValid
import com.example.marvelapp30.core.utils.isPasswordValid
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiEvent

class SignupViewModel : BaseViewModel<AuthUiEvent>() {
    fun checkEntries(username: String, email: String, password: String) {
        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            when {
                username.trim().length in 1..3 -> sendEvent(
                    AuthUiEvent.UsernameError,
                    AuthUiEvent.FormValid(false)
                )

                !email.trim().isEmailValid() -> sendEvent(
                    AuthUiEvent.EmailError,
                    AuthUiEvent.FormValid(false)
                )

                !password.trim().isPasswordValid() -> sendEvent(
                    AuthUiEvent.PasswordError,
                    AuthUiEvent.FormValid(false)
                )

                else -> {
                    sendEvent(AuthUiEvent.FormValid(true))
                }
            }
        }
    }

    fun signupPressed(username: String, email: String, password: String) {
        sendEvent(AuthUiEvent.OnSubmit(username, email, password))
    }
}