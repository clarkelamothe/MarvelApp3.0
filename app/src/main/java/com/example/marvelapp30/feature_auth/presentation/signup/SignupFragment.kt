package com.example.marvelapp30.feature_auth.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentSignupBinding
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiEvent
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiEvent.EmailError
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiEvent.FormValid
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiEvent.PasswordError
import com.example.marvelapp30.feature_auth.presentation.model.AuthUiEvent.UsernameError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : BaseFragment<FragmentSignupBinding>(
    FragmentSignupBinding::inflate
) {
    private var auth = Firebase.auth
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
        binding?.tvSignup?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.btSignup?.setOnClickListener {
            viewModel.signupPressed(
                binding?.etName?.text.toString(),
                binding?.etEmail?.text.toString(),
                binding?.etPassword?.text.toString()
            )
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    UsernameError -> binding?.etName?.error =
                        context?.getString(R.string.username_not_valid_error)

                    EmailError -> binding?.etEmail?.error =
                        context?.getString(R.string.email_not_valid_error)

                    PasswordError -> binding?.etPassword?.error =
                        context?.getString(R.string.password_not_valid_error)

                    is FormValid -> binding?.btSignup?.isEnabled = event.isValid
                    is AuthUiEvent.OnSubmit -> signup(event.email, event.password)
                }
            }
        }
    }

    private fun signup(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            context?.getString(R.string.success_message),
                            Toast.LENGTH_SHORT
                        ).show()
                        auth.signOut()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(
                            context,
                            context?.getString(R.string.failed_message) + "${task.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}