package com.example.marvelapp30.feature_auth.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentSignupBinding
import com.example.marvelapp30.feature_auth.presentation.model.SignupUiEvent.EmailError
import com.example.marvelapp30.feature_auth.presentation.model.SignupUiEvent.FormValid
import com.example.marvelapp30.feature_auth.presentation.model.SignupUiEvent.PasswordError
import com.example.marvelapp30.feature_auth.presentation.model.SignupUiEvent.SignupPressed
import com.example.marvelapp30.feature_auth.presentation.model.SignupUiEvent.UsernameError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
                    it.etPassword.text.toString(),
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
            viewModel.eventFlow.collect { event ->
                when (event) {
                    UsernameError -> binding?.etName?.error = "Username not valid."
                    EmailError -> binding?.etEmail?.error = "Email not valid."
                    PasswordError -> binding?.etPassword?.error = "Password not valid."
                    FormValid -> binding?.btSignup?.isEnabled = true
                    is SignupPressed -> signup(event.email, event.password)
                }
            }
        }
    }

    private fun signup(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}