package com.example.marvelapp30.feature_auth.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentLoginBinding
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent.EmailError
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent.FormValid
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent.LoginPressed
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent.PasswordError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private var auth = Firebase.auth
    private val viewModel: LoginViewModel by viewModel()

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateTo(LoginFragmentDirections.goToCharacters())
        }
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
            viewModel.loginPressed(
                binding?.etEmail?.text.toString(),
                binding?.etPassword?.text.toString()
            )
        }

        binding?.tvSignup?.setOnClickListener {
            navigateTo(LoginFragmentDirections.goToSignup())
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    EmailError -> binding?.etEmail?.error =
                        context?.getString(R.string.email_not_valid_error)

                    PasswordError -> binding?.etPassword?.error =
                        context?.getString(R.string.password_not_valid_error)

                    FormValid -> binding?.btLogin?.isEnabled = true
                    is LoginPressed -> login(event.email, event.password)
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            context?.getString(R.string.success_message),
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateTo(LoginFragmentDirections.goToCharacters())
                    } else {
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