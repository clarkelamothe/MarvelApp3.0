package com.example.marvelapp30.feature_auth.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentLoginBinding
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiIntent.Login
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiIntent.Signup
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.EmailError
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.Error
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.FormValid
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.Idle
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.Loading
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.NavigateToHome
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.NavigateToSignup
import com.example.marvelapp30.feature_auth.presentation.login.model.LoginUiState.PasswordError
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel: LoginViewModel by viewModel()

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
                Login(
                    binding?.etEmail?.text.toString(),
                    binding?.etPassword?.text.toString()
                )
            )
        }

        binding?.tvSignup?.setOnClickListener {
            viewModel.sendEvent(Signup)
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    Idle -> {
                        viewModel.isUserAlreadyLoggedIn()
                    }

                    Loading -> {}
                    EmailError -> binding?.etEmail?.error =
                        context?.getString(R.string.email_not_valid_error)

                    PasswordError -> binding?.etPassword?.error =
                        context?.getString(R.string.password_not_valid_error)

                    is FormValid -> binding?.btLogin?.isEnabled = state.isValid
                    NavigateToSignup -> {
                        navigateTo(R.id.signupFragment)
                    }

                    NavigateToHome -> {
                        navigateTo(R.id.characterFragment)
                    }

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