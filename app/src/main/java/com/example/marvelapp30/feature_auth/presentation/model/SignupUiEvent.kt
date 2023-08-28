package com.example.marvelapp30.feature_auth.presentation.model

sealed class SignupUiEvent {
    object UsernameError : SignupUiEvent()
    object EmailError : SignupUiEvent()
    object PasswordError : SignupUiEvent()
    object FormValid : SignupUiEvent()
    data class SignupPressed(val username: String, val email: String, val password: String) : SignupUiEvent()
}