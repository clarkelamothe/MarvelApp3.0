package com.example.marvelapp30

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelapp30.feature_character.presentation.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterViewModel
    }
}