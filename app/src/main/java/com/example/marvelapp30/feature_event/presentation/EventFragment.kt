package com.example.marvelapp30.feature_event.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { latestNewsUiState ->
                    when (latestNewsUiState) {
                        LatestNewsUiState.Loading -> {
                            Toast.makeText(context, "Loading!", Toast.LENGTH_SHORT).show()
                        }

                        is LatestNewsUiState.Success -> {
                            setAdapter(latestNewsUiState.characters)
                        }

                        is LatestNewsUiState.Error -> {
                            Toast.makeText(context, latestNewsUiState.msg, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter(characters: List<EventData>) {
        eventAdapter = EventAdapter(characters) {
            showComicsForEvent(it)
        }

        binding?.rvEvents?.let {
            it.adapter = eventAdapter
            it.addItemDecoration(MarginItemDecorator())
        }
    }

    private fun showComicsForEvent(event: EventData) {
        // TODO:
        Toast.makeText(context, "You clicked: ${event.id}", Toast.LENGTH_SHORT).show()
        Log.d("Comics", "showComicsForEvent -> id: ${event.id} -> comics: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}