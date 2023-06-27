package com.example.marvelapp30.feature_event.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvelapp30.databinding.FragmentEventBinding
import com.example.marvelapp30.utils.MarginItemDecorator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : Fragment() {
    private var binding: FragmentEventBinding? = null
    private val viewModel: EventViewModel by viewModel()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)

        viewModel.getData()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { latestNewsUiState ->
                    when (latestNewsUiState) {
                        LatestNewsUiState.Loading -> {
                            // TODO()
                        }

                        is LatestNewsUiState.Success -> {
                            setAdapter(latestNewsUiState.characters)
                        }

                        is LatestNewsUiState.Error -> {
//                            TODO()
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter(characters: List<UiEvent>) {
        eventAdapter = EventAdapter(characters)

        binding?.rvEvents?.let {
            it.adapter = eventAdapter
            it.addItemDecoration(MarginItemDecorator())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}