package com.example.marvelapp30.feature_character.presentation.detail

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.marvelapp30.R
import com.example.marvelapp30.databinding.FragmentCharacterDetailBinding
import com.example.marvelapp30.utils.loadUrl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment() {

    private var binding: FragmentCharacterDetailBinding? = null
    private val args: CharacterDetailFragmentArgs by navArgs()
    private val viewModel: CharacterDetailViewModel by viewModel()
    private lateinit var adapter: ComicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater)

        setActionBar()
        designUi()
        getComics()
        setCollector()

        return binding?.root
    }

    private fun designUi() {
        binding?.let {
            args.character.imageUrl.loadUrl(it.ivCharacterImage)
            it.tvCharacterDescription.text = args.character.description
        }
    }

    private fun setCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        ComicState.Loading -> showLoading(true)
                        is ComicState.Success -> {
                            showLoading(false)
                            adapter = ComicAdapter(uiState.comics)

                            binding?.comicsList?.rvComics?.let {
                                it.adapter = adapter
                                it.addItemDecoration(
                                    DividerItemDecoration(
                                        it.context, DividerItemDecoration.VERTICAL
                                    )
                                )
                            }
                        }

                        is ComicState.Error -> {
                            showLoading(false)
                            binding?.comicsList?.rvComics?.let {
                                Snackbar.make(
                                    it,
                                    uiState.msg ?: getString(R.string.error_generic),
                                    Snackbar.LENGTH_INDEFINITE
                                )
                                    .setAction(getString(R.string.retry_snackbar_action)) { getComics() }
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getComics() {
        viewModel.getComics(args.character.id)
    }

    private fun showLoading(show: Boolean) {
        binding?.incLoading?.pbLoading?.isVisible = show
    }

    private fun setActionBar() {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = args.character.name
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}