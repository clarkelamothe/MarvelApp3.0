package com.example.marvelapp30.feature_auth.presentation.signup

import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.feature_auth.presentation.model.SignupUiEvent
import com.example.marvelapp30.core.utils.isEmailValid
import com.example.marvelapp30.core.utils.isPasswordValid

class SignupViewModel : BaseViewModel<SignupUiEvent>() {
    fun checkEntries(username: String, email: String, password: String) {
        when {
            username.length <= 3 -> sendEvent(SignupUiEvent.UsernameError)
            !email.isEmailValid() -> sendEvent(SignupUiEvent.EmailError)
            !password.isPasswordValid() -> sendEvent(SignupUiEvent.PasswordError)
            else -> {
                sendEvent(SignupUiEvent.FormValid)
            }
        }
    }

    fun signupPressed(username: String, email: String, password: String) {
        sendEvent(SignupUiEvent.SignupPressed(username, email, password))
    }
}