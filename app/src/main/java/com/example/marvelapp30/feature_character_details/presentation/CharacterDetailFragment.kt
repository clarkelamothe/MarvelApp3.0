package com.example.marvelapp30.feature_character_details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marvelapp30.R
import com.example.marvelapp30.databinding.FragmentCharacterDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment() {

    private var binding: FragmentCharacterDetailBinding? = null
    private val viewModel: CharacterDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater)

        setActionBar()

        return binding?.root
    }

    private fun setActionBar() {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "My New Title"
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}