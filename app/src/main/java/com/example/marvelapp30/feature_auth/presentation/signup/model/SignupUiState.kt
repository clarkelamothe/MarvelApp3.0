package com.example.marvelapp30.feature_auth.presentation.signup.model

sealed class SignupUiState {
    data object Idle : SignupUiState()
    data object Loading : SignupUiState()
    data object Error : SignupUiState()
    data object EmailError : SignupUiState()
    data object UsernameError : SignupUiState()
    data object PasswordError : SignupUiState()
    data object NavigateToLogin : SignupUiState()
    data class FormValid(val isValid: Boolean) : SignupUiState()
}
