package com.example.marvelapp30.feature_auth.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentSignupBinding
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiIntent.Login
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiIntent.Signup
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.EmailError
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.FormValid
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.Navigate
import com.example.marvelapp30.feature_auth.presentation.signup.model.SignupUiState.PasswordError
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : BaseFragment<FragmentSignupBinding>(
    FragmentSignupBinding::inflate
) {
    private val viewModel: SignupViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showAppBar(false)

        checkEntries()
        setListeners()
        setCollectors()
    }

    private fun checkEntries() {
        binding?.let {
            it.etName.doAfterTextChanged { username ->
                viewModel.checkEntries(
                    username.toString(),
                    it.etEmail.text.toString(),
                    it.etPassword.text.toString()
                )
            }

            it.etEmail.doAfterTextChanged { email ->
                viewModel.checkEntries(
                    it.etName.text.toString(),
                    email.toString(),
                    it.etPassword.text.toString()
                )
            }

            it.etPassword.doAfterTextChanged { password ->
                viewModel.checkEntries(
                    it.etName.text.toString(),
                    it.etEmail.text.toString(),
                    password.toString()
                )
            }
        }
    }

    private fun setListeners() {
        binding?.tvBackToLogin?.setOnClickListener {
            viewModel.sendEvent(Login)
        }

        binding?.btSignup?.setOnClickListener {
            viewModel.sendEvent(
                Signup(
                    binding?.etName?.text.toString(),
                    binding?.etEmail?.text.toString(),
                    binding?.etPassword?.text.toString()
                )
            )
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                when (it) {
                    SignupUiState.Idle -> {}
                    SignupUiState.Loading -> {}
                    SignupUiState.UsernameError -> {
                        binding?.etName?.error =
                            context?.getString(R.string.username_not_valid_error)
                    }

                    EmailError -> {
                        binding?.etEmail?.error =
                            context?.getString(R.string.email_not_valid_error)
                    }

                    PasswordError -> {
                        binding?.etPassword?.error =
                            context?.getString(R.string.password_not_valid_error)
                    }

                    is FormValid -> {
                        binding?.btSignup?.isEnabled = it.isValid
                    }

                    SignupUiState.Error -> {
                        Toast.makeText(
                            context,
                            context?.getString(R.string.error_generic),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Navigate -> navigateTo(R.id.loginFragment)
                }
            }
        }
    }
}
