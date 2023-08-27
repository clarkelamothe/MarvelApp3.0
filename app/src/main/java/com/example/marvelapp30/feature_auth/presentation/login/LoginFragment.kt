package com.example.marvelapp30.feature_auth.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.marvelapp30.databinding.FragmentLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private var auth = Firebase.auth

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("FirebaseUser", "onStart: ${currentUser.displayName}")
            goTo(LoginFragmentDirections.goToCharacters())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.hide()

        setListeners()

        return binding?.root
    }

    private fun setListeners() {
        binding?.btLogin?.setOnClickListener {
            login(
                binding?.etEmail?.text.toString(),
                binding?.etPassword?.text.toString()
            )
        }

        binding?.tvSignup?.setOnClickListener {
            goTo(LoginFragmentDirections.goToSignup())
        }
    }

    private fun login(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                        goTo(LoginFragmentDirections.goToCharacters())
                    } else {
                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun goTo(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}