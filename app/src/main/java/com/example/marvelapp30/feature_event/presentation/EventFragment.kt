package com.example.marvelapp30.feature_event.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.databinding.FragmentEventBinding
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.feature_event.domain.model.Event
import com.example.marvelapp30.utils.MarginItemDecorator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : BaseFragment<FragmentEventBinding>(
    FragmentEventBinding::inflate
) {
    private val viewModel: EventViewModel by viewModel()
    private lateinit var eventAdapter: EventAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.main_title))
        showAppBar()

        viewModel.getData()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { latestNewsUiState ->
                    when (latestNewsUiState) {
                        LatestNewsUiState.Loading -> {
                            showLoading(true)
                        }

                        is LatestNewsUiState.Success -> {
                            showLoading()
                            setAdapter(latestNewsUiState.events)
                        }

                        is LatestNewsUiState.Error -> {
                            showLoading()
                            showError(latestNewsUiState.msg)
                        }
                    }
                }
            }
        }
    }

    private fun showError(msg: String?) {

        binding?.root?.let {
            Snackbar.make(
                it,
                msg ?: getText(R.string.error_generic),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getText(R.string.retry_snackbar_action)) { viewModel.getData() }.show()
        }
    }

    private fun setAdapter(events: List<Event>) {
        eventAdapter = EventAdapter(events) {
            viewModel.getComics(it.id)
            showComicsForEvent()
        }

        binding?.rvEvents?.let {
            it.adapter = eventAdapter
            it.addItemDecoration(MarginItemDecorator())
        }
    }

    private fun showLoading(show: Boolean = false) {
        binding?.incLoading?.pbLoading?.isVisible = show
    }

    private fun showComicsForEvent(): List<Comic> {
        var comics = emptyList<Comic>()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.comics.collectLatest {
                    comics = it
                }
            }
        }
        return comics
    }
}