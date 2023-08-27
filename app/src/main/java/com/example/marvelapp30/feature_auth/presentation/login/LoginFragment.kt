package com.example.marvelapp30.feature_auth.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.marvelapp30.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
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
            goTo(LoginFragmentDirections.goToCharacters())
        }

        binding?.tvSignup?.setOnClickListener {
            goTo(LoginFragmentDirections.goToSignup())
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