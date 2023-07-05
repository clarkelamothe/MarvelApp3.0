package com.example.marvelapp30.feature_event.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp30.databinding.EventItemBinding
import com.example.marvelapp30.feature_event.domain.model.Event

class EventAdapter(
    private val events: List<Event>,
    private val onItemClicked: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
        return EventViewHolder(
            EventItemBinding.inflate(view, parent, false)
        ) { onItemClicked(events[it]) }
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    inner class EventViewHolder(
        binding: EventItemBinding,
        onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btExpanded.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
        }

        private val ivImage = binding.eventImage
        private val tvDate = binding.eventDate
        private val tvName = binding.eventName
        private val btExpanded = binding.btExpanded

        fun bind(event: Event) {
            tvName.text = event.title
            tvDate.text = event.date
            Glide.with(ivImage.context).load(event.imageUrl).into(ivImage)
        }
    }
}