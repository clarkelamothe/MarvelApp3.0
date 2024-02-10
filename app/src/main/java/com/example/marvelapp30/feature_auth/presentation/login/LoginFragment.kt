package com.example.marvelapp30.feature_auth.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentLoginBinding
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiIntent
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.EmailError
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.Error
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.FormValid
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.Idle
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.Loading
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.NavigateToHome
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.NavigateToSignup
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiState.PasswordError
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel: LoginViewModel by viewModel()

    override fun onStart() {
        super.onStart()

//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            navigateTo(LoginFragmentDirections.goToCharacters())
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        showAppBar(false)

        checkEntries()
        setListeners()
        setCollectors()
    }

    private fun checkEntries() {
        binding?.let {
            it.etEmail.doAfterTextChanged { email ->
                viewModel.checkEntries(
                    email.toString(), it.etPassword.text.toString()
                )
            }

            it.etPassword.doAfterTextChanged { password ->
                viewModel.checkEntries(
                    it.etEmail.text.toString(), password.toString()
                )
            }
        }
    }

    private fun setListeners() {
        binding?.btLogin?.setOnClickListener {
            viewModel.sendEvent(
                AuthUiIntent.Login(
                    binding?.etEmail?.text.toString(),
                    binding?.etPassword?.text.toString()
                )
            )
        }

        binding?.tvSignup?.setOnClickListener {
            viewModel.sendEvent(AuthUiIntent.Signup)
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    Idle -> {}
                    Loading -> {}
                    EmailError -> binding?.etEmail?.error =
                        context?.getString(R.string.email_not_valid_error)
                    PasswordError -> binding?.etPassword?.error =
                        context?.getString(R.string.password_not_valid_error)
                    is FormValid -> binding?.btLogin?.isEnabled = state.isValid
                    NavigateToSignup -> navigateTo(LoginFragmentDirections.goToSignup())
                    NavigateToHome -> navigateTo(LoginFragmentDirections.goToCharacters())
                    Error -> {
                        Toast.makeText(
                            context,
                            context?.getString(R.string.failed_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}