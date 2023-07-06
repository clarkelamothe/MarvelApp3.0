package com.example.marvelapp30.feature_character.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.marvelapp30.databinding.ComicItemBinding
import com.example.marvelapp30.feature_character.domain.model.Comic

class ComicAdapter(
    private val comics: List<Comic>,
) : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ComicViewHolder(
        ComicItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = comics.size

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(comics[position])
    }

    inner class ComicViewHolder(
        binding: ComicItemBinding,
    ) : ViewHolder(binding.root) {

        private val tvTitle = binding.comicTitle
        private val tvYear = binding.comicYear

        fun bind(comic: Comic) {
            tvTitle.text = comic.title
            tvYear.text = comic.year.toString()
        }
    }
}