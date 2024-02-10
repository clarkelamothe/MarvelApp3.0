package com.example.marvelapp30.feature_auth.presentation.signup.model

sealed class SignupUiIntent {
    data class Signup(
        val username: String,
        val email: String,
        val password: String
    ) : SignupUiIntent()

    data object Login: SignupUiIntent()
    data class OnFormFilling(
        val username: String,
        val email: String, val password: String
    ) : SignupUiIntent()
}
