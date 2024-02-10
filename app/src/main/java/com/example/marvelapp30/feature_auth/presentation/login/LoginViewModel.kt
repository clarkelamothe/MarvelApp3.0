package com.example.marvelapp30.feature_auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.core.utils.isEmailValid
import com.example.marvelapp30.core.utils.isPasswordValid
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiIntent
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiIntent.Login
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiIntent.LoginFacebook
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiIntent.OnFormFilling
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiIntent.Signup
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.EmailError
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.Error
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.FormValid
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.Idle
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.Loading
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.NavigateToHome
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.NavigateToSignup
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.PasswordError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<LoginUiIntent>() {
    private val _state = MutableStateFlow<LoginUiState>(Idle)
    val state = _state.asStateFlow()

    private var auth = Firebase.auth

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collect {
                _state.update { Loading }
                when (it) {
                    LoginFacebook -> {}
                    is OnFormFilling -> {
                        checkEntries(it.email, it.password)
                    }

                    Signup -> {
                        _state.update { NavigateToSignup }
                    }

                    is Login -> {
                        login(it.email, it.password)
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

    fun isUserAlreadyLoggedIn() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _state.update { NavigateToHome }
        }
    }
}
