package com.example.marvelapp30.feature_event.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvelapp30.R
import com.example.marvelapp30.databinding.FragmentEventBinding
import com.example.marvelapp30.utils.MarginItemDecorator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : Fragment() {
    private var binding: FragmentEventBinding? = null
    private val viewModel: EventViewModel by viewModel()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.main_title)

        viewModel.getData()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { latestNewsUiState ->
                    when (latestNewsUiState) {
                        LatestNewsUiState.Loading -> {
                            showLoading(true)
                        }

                        is LatestNewsUiState.Success -> {
                            showLoading(false)
                            setAdapter(latestNewsUiState.characters)
                        }

                        is LatestNewsUiState.Error -> {
                            showLoading(false)
                            showError(latestNewsUiState.msg)
                        }
                    }
                }
            }
        }
    }

    private fun showError(msg: String?) {
        binding?.inLoading?.loading?.isVisible = false

        binding?.root?.let { it1 ->
            Snackbar.make(
                it1,
                msg ?: "Something went wrong!",
                Snackbar.LENGTH_INDEFINITE
            ).setAction("RETRY") { viewModel.getData() }.show()
        }
    }

    private fun setAdapter(characters: List<EventData>) {
        binding?.inLoading?.loading?.isVisible = false

        eventAdapter = EventAdapter(characters) {
            showComicsForEvent(it)
        }

        binding?.rvEvents?.let {
            it.adapter = eventAdapter
            it.addItemDecoration(MarginItemDecorator())
        }
    }

    private fun showLoading(show: Boolean) {
        binding?.inLoading?.loading?.isVisible = show
    }

    private fun showComicsForEvent(event: EventData) {
        // TODO:
        Log.d("Comics", "showComicsForEvent -> id: ${event.id} -> comics: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}