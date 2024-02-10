package com.example.marvelapp30.feature_auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.core.utils.isEmailValid
import com.example.marvelapp30.core.utils.isPasswordValid
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiIntent
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.EmailError
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.Error
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.FormValid
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.Loading
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.NavigateToHome
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.NavigateToSignup
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.PasswordError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<AuthUiIntent>() {
    private val _state = MutableStateFlow<AuthUiState>(Loading)
    val state = _state.asStateFlow()

    private var auth = Firebase.auth

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            eventFlow.collect {
                when (it) {
                    is AuthUiIntent.Login -> {
                        login(it.email, it.password)
                    }

                    AuthUiIntent.LoginFacebook -> {}
                    is AuthUiIntent.OnFormFilling -> {
                        checkEntries(it.email, it.password)
                    }

                    AuthUiIntent.Signup -> {
                        _state.update { NavigateToSignup }
                    }
                }
            }
        }
    }

    fun checkEntries(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            when {
                !email.trim().isEmailValid() && email.trim().isNotBlank() -> {
                    _state.update { EmailError }
                    _state.update { FormValid(false) }
                }

                !password.trim().isPasswordValid() && password.trim().isNotBlank() -> {
                    _state.update { PasswordError }
                    _state.update { FormValid(false) }
                }

                else -> {
                    _state.update { FormValid(true) }
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _state.update { NavigateToHome }
                    } else {
                        _state.update { Error }
                    }
                }
        }
    }
}