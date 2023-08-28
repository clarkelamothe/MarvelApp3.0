package com.example.marvelapp30.feature_auth.presentation.model

sealed class LoginUiEvent {
    object EmailError : LoginUiEvent()
    object PasswordError : LoginUiEvent()
    object FormValid : LoginUiEvent()
    data class LoginPressed(val email: String, val password: String) : LoginUiEvent()
}
