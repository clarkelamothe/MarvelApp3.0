package com.example.marvelapp30.feature_character.presentation.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.marvelapp30.feature_character.domain.model.Character
import com.example.marvelapp30.utils.MarginItemDecorator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.io.IOException

class CharacterFragment : Fragment() {

    private var binding: FragmentCharacterBinding? = null
    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterAdapter = CharacterAdapter {
            goToDetails(it)
        }
    }

    private fun goToDetails(character: Character) {
        R.id.goToDetail
        findNavController().navigate(
            CharacterFragmentDirections.goToDetail(character)
        )
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
            characterAdapter.loadStateFlow.collectLatest { loadState ->
                binding?.inLoading?.loading?.isVisible = loadState.refresh is LoadState.Loading

                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                when (val throwable = errorState?.error) {
                    is IOException, is HttpException -> {
                        binding?.rvCharacter?.let { rv ->
                            Snackbar.make(
                                rv,
                                throwable.message.toString(),
                                Snackbar.LENGTH_INDEFINITE
                            ).setAction("RETRY") { characterAdapter.refresh() }.show()
                        }
                    }

                    is NullPointerException -> {
                        Toast.makeText(context, "Something happened!", Toast.LENGTH_SHORT).show()
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
                viewModel.characters.collectLatest {
                    characterAdapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}