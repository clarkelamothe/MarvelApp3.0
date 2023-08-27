package com.example.marvelapp30.feature_auth.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.marvelapp30.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private var binding: FragmentSignupBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.hide()

        setListeners()

        return binding?.root
    }

    private fun setListeners() {
        binding?.tvSignup?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.btSignup?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}