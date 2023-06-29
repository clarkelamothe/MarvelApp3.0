package com.example.marvelapp30.feature_character.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.marvelapp30.databinding.CharacterItemBinding
import com.example.marvelapp30.feature_character.data.local.CharacterEntity

class CharacterAdapter(
    private val onItemClick: (CharacterEntity) -> Unit
) : PagingDataAdapter<CharacterEntity, CharacterAdapter.CharacterViewHolder>(CharacterComparator) {
    object CharacterComparator : DiffUtil.ItemCallback<CharacterEntity>() {
        override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CharacterEntity,
            newItem: CharacterEntity
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
        return CharacterViewHolder(CharacterItemBinding.inflate(view))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class CharacterViewHolder(binding: CharacterItemBinding) :
        ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { character ->
                    onItemClick(character)
                }
            }
        }

        private val ivImage = binding.characterImage
        private val tvName = binding.characterName
        private val tvDescription = binding.characterDescription

        fun bind(character: CharacterEntity) {
            Glide.with(ivImage.context).load(character.imageUrl).into(ivImage)
            tvName.text = character.name
            tvDescription.text = character.description
        }
    }
}