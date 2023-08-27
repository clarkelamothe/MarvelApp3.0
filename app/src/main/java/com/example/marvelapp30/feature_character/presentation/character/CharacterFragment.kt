package com.example.marvelapp30.feature_character.presentation.character

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentCharacterBinding
import com.example.marvelapp30.feature_character.domain.model.Character
import com.example.marvelapp30.utils.MarginItemDecorator
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
            goToDetails(it)
        }
    }

    private fun goToDetails(character: Character) {
        navigateTo(
            CharacterFragmentDirections.goToDetail(character)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(getString(R.string.main_title))

        setAdapter()
        setLoadingState()
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
                        binding?.rvCharacters?.let { rv ->
                            Snackbar.make(
                                rv,
                                throwable.message ?: getString(R.string.error_generic),
                                Snackbar.LENGTH_INDEFINITE
                            )
                                .setAction(getString(R.string.retry_snackbar_action)) { characterAdapter.refresh() }
                                .show()
                        }
                    }

                    is NullPointerException -> {
                        Toast.makeText(
                            context,
                            getString(R.string.error_generic),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setAdapter() {

        binding?.let {
            it.rvCharacters.adapter = characterAdapter
            it.rvCharacters.addItemDecoration(MarginItemDecorator())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collectLatest {
                    characterAdapter.submitData(it)
                }
            }
        }
    }
}