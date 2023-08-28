package com.example.marvelapp30.feature_auth.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentLoginBinding
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent.EmailError
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent.FormValid
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent.LoginPressed
import com.example.marvelapp30.feature_auth.presentation.model.LoginUiEvent.PasswordError
import com.google.android.material.textfield.TextInputEditText
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
            Log.d("FirebaseUser", "onStart: ${currentUser.displayName}")
            navigateTo(LoginFragmentDirections.goToCharacters())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.hide()

        checkEntries()
        setListeners()
        setCollectors()
    }

    private fun checkEntries() {
        binding?.let {
            it.etEmail.afterTextChanged { email ->
                viewModel.checkEntries(
                    email, it.etPassword.text.toString()
                )
            }

            it.etPassword.afterTextChanged { password ->
                viewModel.checkEntries(
                    it.etEmail.text.toString(), password
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
                    EmailError -> binding?.etEmail?.error = "Email not valid."
                    PasswordError -> binding?.etPassword?.error = "Password not valid."
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
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                        navigateTo(LoginFragmentDirections.goToCharacters())
                    } else {
                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}