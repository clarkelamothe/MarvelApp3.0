package com.example.marvelapp30.feature_character.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvelapp30.databinding.FragmentCharacterBinding
import com.example.marvelapp30.utils.MarginItemDecorator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterFragment : Fragment() {

    private var binding: FragmentCharacterBinding? = null
    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterAdapter = CharacterAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterBinding.inflate(inflater)

        setAdapter()

        return binding?.root
    }

    private fun setAdapter() {

        binding?.let {
            it.rvCharacter.adapter = characterAdapter
            it.rvCharacter.addItemDecoration(MarginItemDecorator())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is CharacterViewModel.LatestNewsUiState.Success -> {
                            characterAdapter.submitData(uiState.characters)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}