package com.example.marvelapp30.feature_character.presentation.character

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.core.ui.MarginItemDecorator
import com.example.marvelapp30.databinding.FragmentCharacterBinding
import com.example.marvelapp30.feature_character.presentation.model.CharacterUiEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.io.IOException

class CharacterFragment : BaseFragment<FragmentCharacterBinding>(
    FragmentCharacterBinding::inflate
) {
    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterAdapter = CharacterAdapter {
            navigateTo(
                CharacterFragmentDirections.goToDetail(it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(getString(R.string.main_title))
        showAppBar()

        viewModel.getCharacters()

        setCollectors()
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow.collect {
                when (it) {
                    CharacterUiEvent.Loading -> setLoadingState()
                    is CharacterUiEvent.Error -> showSnackBar(it.message)
                    is CharacterUiEvent.Success -> {
                        setAdapter()
                        characterAdapter.submitData(it.characters)
                    }
                }
            }
        }
    }

    private fun setLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            characterAdapter.loadStateFlow.collectLatest { loadState ->
                binding?.incLoading?.pbLoading?.isVisible = loadState.refresh is LoadState.Loading

                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                when (val throwable = errorState?.error) {
                    is IOException, is HttpException -> {
                        viewModel.sendError(throwable.message)
                    }

                    is NullPointerException -> {
                        viewModel.sendError(getString(R.string.error_generic))
                    }
                }
            }
        }
    }

    private fun showSnackBar(message: String?) {
        binding?.rvCharacters?.let { rv ->
            Snackbar.make(
                rv,
                message ?: getString(R.string.error_generic),
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(getString(R.string.retry_snackbar_action)) { characterAdapter.refresh() }
                .show()
        }
    }

    private fun setAdapter() {
        binding?.let {
            it.rvCharacters.adapter = characterAdapter
            it.rvCharacters.addItemDecoration(MarginItemDecorator())
        }
    }
}