package com.example.marvelapp30.feature_character.presentation.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.marvelapp30.databinding.CharacterItemBinding
import com.example.marvelapp30.feature_character.domain.model.Character
import com.example.marvelapp30.utils.loadUrl

class CharacterAdapter(
    private val onItemClick: (Character) -> Unit
) : PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(CharacterComparator) {
    object CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Character,
            newItem: Character
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
        return CharacterViewHolder(CharacterItemBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.setIsRecyclable(true)
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

        private val ivImage = binding.ivCharacterImage
        private val tvName = binding.tvCharacterName
        private val tvDescription = binding.tvCharacterDescription

        fun bind(character: Character) {
            character.imageUrl.loadUrl(ivImage)
            tvName.text = character.name
            tvDescription.text = character.description
        }
    }
}