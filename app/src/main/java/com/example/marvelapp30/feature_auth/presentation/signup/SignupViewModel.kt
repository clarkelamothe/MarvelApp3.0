package com.example.marvelapp30.feature_auth.presentation.signup

import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.core.utils.isEmailValid
import com.example.marvelapp30.core.utils.isPasswordValid
import com.example.marvelapp30.feature_auth.presentation.model.SignupUiEvent

class SignupViewModel : BaseViewModel<SignupUiEvent>() {
    fun checkEntries(username: String, email: String, password: String) {
        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            when {
                username.trim().length in 1..3 -> sendEvent(
                    SignupUiEvent.UsernameError,
                    SignupUiEvent.FormValid(false)
                )

                !email.trim().isEmailValid() -> sendEvent(
                    SignupUiEvent.EmailError,
                    SignupUiEvent.FormValid(false)
                )

                !password.trim().isPasswordValid() -> sendEvent(
                    SignupUiEvent.PasswordError,
                    SignupUiEvent.FormValid(false)
                )

                else -> {
                    sendEvent(SignupUiEvent.FormValid(true))
                }
            }
        }
    }

    fun signupPressed(username: String, email: String, password: String) {
        sendEvent(SignupUiEvent.SignupPressed(username, email, password))
    }
}