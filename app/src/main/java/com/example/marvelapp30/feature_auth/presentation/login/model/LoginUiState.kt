package com.example.marvelapp30.feature_auth.presentation.login.model

sealed class LoginUiState {
    data object Idle : LoginUiState()
    data object Loading : LoginUiState()
    data object Error : LoginUiState()
    data object EmailError : LoginUiState()
    data object PasswordError : LoginUiState()
    data object NavigateToSignup : LoginUiState()
    data object NavigateToHome : LoginUiState()
    data class FormValid(val isValid: Boolean) : LoginUiState()
}
