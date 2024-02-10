package com.example.marvelapp30.feature_character.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.core.utils.loadUrl
import com.example.marvelapp30.databinding.FragmentCharacterDetailBinding
import com.example.marvelapp30.feature_character.presentation.detail.ComicState.Error
import com.example.marvelapp30.feature_character.presentation.detail.ComicState.Loading
import com.example.marvelapp30.feature_character.presentation.detail.ComicState.Success
import com.example.marvelapp30.feature_character.presentation.detail.ComicUiIntent.FetchComics
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>(
    FragmentCharacterDetailBinding::inflate
) {
    private val args: CharacterDetailFragmentArgs by navArgs()
    private val viewModel: CharacterDetailViewModel by viewModel()
    private lateinit var adapter: ComicAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(args.character.name)
        showAppBar()
        setActionBar()
        designUi()
        setCollector()

        getComics()
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
                viewModel.state.collectLatest { state ->
                    Log.i(this@CharacterDetailFragment::class.simpleName, "setCollector: $state")
                    when (state) {
                        Loading -> showLoading(true)
                        is Success -> {
                            showLoading(false)
                            bindList(state)
                        }

                        is Error -> {
                            showLoading(false)
                            showSnackbar(state)
                        }
                    }
                }
            }
        }
    }

    private fun showSnackbar(state: Error) {
        binding?.comicsList?.rvComics?.let {
            Snackbar.make(
                it,
                state.msg ?: getString(R.string.error_generic),
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(getString(R.string.retry_snackbar_action)) { getComics() }
                .show()
        }
    }

    private fun bindList(state: Success) {
        adapter = ComicAdapter(state.comics)

        binding?.comicsList?.rvComics?.let {
            it.adapter = adapter
            it.addItemDecoration(
                DividerItemDecoration(
                    it.context, DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun getComics() {
        viewModel.sendEvent(FetchComics(args.character.id))
    }

    private fun showLoading(show: Boolean) {
        binding?.incLoading?.pbLoading?.isVisible = show
    }

    private fun setActionBar() {
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
    }
}