package com.example.marvelapp30.feature_character.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.marvelapp30.R
import com.example.marvelapp30.databinding.FragmentCharacterBinding
import com.example.marvelapp30.utils.MarginItemDecorator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterFragment : Fragment() {

    private var binding: FragmentCharacterBinding? = null
    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterAdapter = CharacterAdapter {
            goToDetails()
        }
    }

    private fun goToDetails() {
        findNavController().navigate(R.id.goToDetail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterBinding.inflate(inflater)

        (activity as AppCompatActivity).supportActionBar?.title = "Marvel Challenge"

        setAdapter()
        setLoadingState()

        return binding?.root
    }

    private fun setLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            characterAdapter.loadStateFlow.collectLatest {
                binding?.inLoading?.loading?.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Error) {
                    binding?.root?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "Something went wrong!",
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction("RETRY") { characterAdapter.refresh() }.show()
                    }
                }
            }
        }
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