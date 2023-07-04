package com.example.marvelapp30.feature_character.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.marvelapp30.R
import com.example.marvelapp30.databinding.FragmentCharacterDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment() {

    private var binding: FragmentCharacterDetailBinding? = null
    private val args: CharacterDetailFragmentArgs by navArgs()
    private val viewModel: CharacterDetailViewModel by viewModel()

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

        return binding?.root
    }

    private fun designUi() {
        binding?.let {
            Glide.with(it.characterImage.context)
                .load(args.character?.imageUrl).into(it.characterImage)

            it.characterDescription.text = args.character?.description
        }
    }

    private fun setActionBar() {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = args.character?.name
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}