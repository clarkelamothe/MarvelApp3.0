package com.example.marvelapp30.feature_auth.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private var auth = Firebase.auth

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

        setListeners()
    }

    private fun setListeners() {
        binding?.btLogin?.setOnClickListener {
            login(
                binding?.etEmail?.text.toString(),
                binding?.etPassword?.text.toString()
            )
        }

        binding?.tvSignup?.setOnClickListener {
            navigateTo(LoginFragmentDirections.goToSignup())
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
}