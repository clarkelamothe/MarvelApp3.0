package com.example.marvelapp30.feature_auth.presentation.model

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data object Error : AuthUiState()
    data object EmailError : AuthUiState()
    data object PasswordError : AuthUiState()
    data object NavigateToSignup : AuthUiState()
    data object NavigateToHome : AuthUiState()
    data class FormValid(val isValid: Boolean) : AuthUiState()
}
