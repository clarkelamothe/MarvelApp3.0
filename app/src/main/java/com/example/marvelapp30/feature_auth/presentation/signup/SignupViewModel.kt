package com.example.marvelapp30.feature_auth.presentation.signup

import androidx.lifecycle.viewModelScope
import com.example.marvelapp30.core.ui.BaseViewModel
import com.example.marvelapp30.core.utils.isEmailValid
import com.example.marvelapp30.core.utils.isPasswordValid
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiIntent
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiIntent.Login
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiIntent.OnFormFilling
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiIntent.Signup
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.EmailError
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.Error
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.FormValid
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.Idle
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.Loading
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.NavigateToLogin
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.PasswordError
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.UsernameError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel : BaseViewModel<SignupUiIntent>() {
    private val _state = MutableStateFlow<SignupUiState>(Idle)
    val state = _state.asStateFlow()

    private var auth = Firebase.auth

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            eventFlow.collect {
                _state.update { Loading }
                when (it) {
                    is OnFormFilling -> {
                        checkEntries(it.username, it.email, it.password)
                    }

                    Login -> {
                        _state.update { NavigateToLogin }
                    }

                    is Signup -> {
                        signup(it.email, it.password)
                    }
                }
            }
        }
    }

    fun checkEntries(username: String, email: String, password: String) {
        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            when {
                username.trim().length in 1..3 -> {
                    _state.update { UsernameError }
                    _state.update { FormValid(false) }
                }

                !email.trim().isEmailValid() -> {
                    _state.update { EmailError }
                    _state.update { FormValid(false) }

                }

                !password.trim().isPasswordValid() -> {
                    _state.update { PasswordError }
                    _state.update { FormValid(false) }
                }

                else -> _state.update { FormValid(true) }
            }
        }
    }

    private fun signup(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.signOut()
                        _state.update { NavigateToLogin }
                    } else {
                        _state.update { Error }
                    }
                }
        }
    }
}