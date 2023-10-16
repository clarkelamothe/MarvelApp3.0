package com.example.marvelapp30.feature_event.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvelapp30.R
import com.example.marvelapp30.core.ui.BaseFragment
import com.example.marvelapp30.core.ui.MarginItemDecorator
import com.example.marvelapp30.databinding.FragmentEventBinding
import com.example.marvelapp30.feature_event.domain.model.Event
import com.example.marvelapp30.feature_event.presentation.LatestNewsUiState.Error
import com.example.marvelapp30.feature_event.presentation.LatestNewsUiState.EventExpanded
import com.example.marvelapp30.feature_event.presentation.LatestNewsUiState.Loading
import com.example.marvelapp30.feature_event.presentation.LatestNewsUiState.Success
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

        setCollectors()
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collectLatest { latestNewsUiState ->
                    binding?.bind(latestNewsUiState)
                }
            }
        }
    }

    private fun FragmentEventBinding.bind(state: LatestNewsUiState) {
        this.incLoading.pbLoading.isVisible = state is Loading
        if (state is Error) showError(state.msg)

        when(state) {
            is EventExpanded -> eventAdapter.notifyItemChanged(state.pos)
            is Success -> setAdapter(state.events)
            else -> {}
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
        eventAdapter = EventAdapter(events) { pos, ev ->
            if (!ev.isExpanded) viewModel.getComics(pos, ev.id)
        }

        binding?.rvEvents?.let {
            it.adapter = eventAdapter
            it.addItemDecoration(MarginItemDecorator())
        }
    }
}