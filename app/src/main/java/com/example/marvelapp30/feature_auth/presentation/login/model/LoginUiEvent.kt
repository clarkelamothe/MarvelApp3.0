package com.example.marvelapp30.feature_auth.presentation.login.model

sealed class LoginUiIntent {
    data class Login(val email: String, val password: String) : LoginUiIntent()
    data object Signup : LoginUiIntent()
    data class OnFormFilling(val email: String, val password: String) : LoginUiIntent()
    data object LoginFacebook : LoginUiIntent()
}
