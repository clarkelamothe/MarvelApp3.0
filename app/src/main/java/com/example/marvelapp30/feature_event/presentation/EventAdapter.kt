package com.example.marvelapp30.feature_event.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp30.databinding.EventItemBinding
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.feature_character.presentation.detail.ComicAdapter
import com.example.marvelapp30.feature_event.domain.model.Event
import com.example.marvelapp30.utils.loadUrl
import com.example.marvelapp30.R.drawable.ic_baseline_keyboard_arrow_down_24 as arrowDown
import com.example.marvelapp30.R.drawable.ic_baseline_keyboard_arrow_up_24 as arrowUp

class EventAdapter(
    private val events: List<Event>,
    private val onItemClicked: (Event) -> List<Comic>
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

            holder.btExpanded.setOnClickListener {
                isAnyItemExpanded(position)
                val expand = events[position].isExpanded
                holder.toggle(!expand)
                events[position].isExpanded = !expand

                events[position].comics = onItemClicked(events[position])
                comicRV.rvComics.adapter = ComicAdapter(events[position].comics)
                holder.comicRV.rvComics.addItemDecoration(
                    DividerItemDecoration(
                        it.context, DividerItemDecoration.VERTICAL
                    )
                )
            }
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
        val btExpanded = binding.ibExpanded
        val comicRV = binding.incComics

        fun bind(event: Event) {
            tvName.text = event.title
            tvDate.text = event.date
            event.imageUrl.loadUrl(ivImage)
            toggle(event.isExpanded)
        }

        fun toggle(expand: Boolean) {
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