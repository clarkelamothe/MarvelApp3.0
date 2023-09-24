package com.example.marvelapp30.feature_event.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp30.core.utils.loadUrl
import com.example.marvelapp30.databinding.EventItemBinding
import com.example.marvelapp30.feature_character.presentation.detail.ComicAdapter
import com.example.marvelapp30.feature_event.domain.model.Event
import com.example.marvelapp30.R.drawable.ic_baseline_keyboard_arrow_down_24 as arrowDown
import com.example.marvelapp30.R.drawable.ic_baseline_keyboard_arrow_up_24 as arrowUp

class EventAdapter(
    private val events: List<Event>,
    private val onItemClicked: (Int, Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        with(holder) {
            bind(events[position])
        }
    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = events.indexOfFirst {
            it.isExpanded
        }
        if (temp >= 0 && temp != position) {
            events[temp].isExpanded = false
            notifyItemChanged(temp)
        }
    }

    inner class EventViewHolder(
        binding: EventItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private val header = binding.tvComicsHeader
        private val ivImage = binding.ivEventImage
        private val tvDate = binding.tvEventDate
        private val tvName = binding.tvEventName
        private val btExpanded = binding.ibExpanded
        private val comicRV = binding.incComics

        init {
            btExpanded.setOnClickListener {
                isAnyItemExpanded(bindingAdapterPosition)
                val expand = events[bindingAdapterPosition].isExpanded
                toggle(!expand)
                events[bindingAdapterPosition].isExpanded = !expand

                onItemClicked(bindingAdapterPosition, events[bindingAdapterPosition])

                val c = events[bindingAdapterPosition].comics
                comicRV.rvComics.adapter = ComicAdapter(c)

                comicRV.rvComics.addItemDecoration(
                    DividerItemDecoration(
                        it.context, DividerItemDecoration.VERTICAL
                    )
                )
            }
        }

        fun bind(event: Event) {
            tvName.text = event.title
            tvDate.text = event.date
            event.imageUrl.loadUrl(ivImage)
            toggle(event.isExpanded)
        }

        private fun toggle(expand: Boolean) {
            comicRV.root.isVisible = expand
            header.isVisible = expand

            btExpanded.setImageDrawable(
                AppCompatResources.getDrawable(
                    itemView.context,
                    if (expand) arrowDown else arrowUp
                )
            )
        }
    }
}