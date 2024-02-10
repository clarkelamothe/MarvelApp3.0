package com.example.marvelapp30.feature_auth.presentation.model

sealed class AuthUiIntent {
    data class Login(val email: String, val password: String) : AuthUiIntent()
    data object Signup : AuthUiIntent()
    data class OnFormFilling(val email: String, val password: String) : AuthUiIntent()
    data object LoginFacebook : AuthUiIntent()
}
