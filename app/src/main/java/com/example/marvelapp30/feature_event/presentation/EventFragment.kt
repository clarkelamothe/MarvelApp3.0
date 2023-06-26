package com.example.marvelapp30.feature_event.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvelapp30.databinding.FragmentEventBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : Fragment() {

    private var binding: FragmentEventBinding? = null
    private val viewModel: EventViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        LatestNewsUiState.Loading -> Toast.makeText(
                            context,
                            "Events are loading!",
                            Toast.LENGTH_SHORT
                        ).show()

                        is LatestNewsUiState.Success -> {
                            Toast.makeText(
                                context,
                                it.characters.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is LatestNewsUiState.Error -> Toast.makeText(
                            context,
                            "Something went wrong with the events",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}