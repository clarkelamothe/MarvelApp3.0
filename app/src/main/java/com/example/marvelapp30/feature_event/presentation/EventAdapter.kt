package com.example.marvelapp30.feature_event.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp30.databinding.EventItemBinding

class EventAdapter(
    private val events: List<UiEvent>
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
        return EventViewHolder(EventItemBinding.inflate(view, parent, false))
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    inner class EventViewHolder(
        binding: EventItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val ivImage = binding.eventImage
        private val tvDate = binding.eventDate
        private val tvName = binding.eventName
        private val btExpanded = binding.btExpanded

        fun bind(event: UiEvent) {
            tvName.text = event.title
            tvDate.text = event.date
            Glide.with(ivImage.context).load(event.imageUrl).into(ivImage)
            btExpanded.isEnabled = true
        }
    }
}