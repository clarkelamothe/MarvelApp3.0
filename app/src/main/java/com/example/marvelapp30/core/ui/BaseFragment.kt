package com.example.marvelapp30.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {
    var binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater)
        return binding?.root
    }

    fun navigateTo(destination: NavDirections) {
        findNavController().navigate(destination)
    }

    fun showAppBar(show: Boolean = true) {
        if (show) {
            (activity as AppCompatActivity).supportActionBar?.show()
        } else {
            (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }

    fun setTitle(name: String) {
        (activity as AppCompatActivity).supportActionBar?.title = name
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}