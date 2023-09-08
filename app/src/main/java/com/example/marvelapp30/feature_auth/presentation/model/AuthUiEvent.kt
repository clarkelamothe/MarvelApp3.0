package com.example.marvelapp30.feature_auth.presentation.model

sealed class AuthUiEvent {
    object UsernameError : AuthUiEvent()
    object EmailError : AuthUiEvent()
    object PasswordError : AuthUiEvent()
    data class FormValid(val isValid: Boolean) : AuthUiEvent()
    data class OnSubmit(val username: String, val email: String, val password: String) : AuthUiEvent()
}