package com.dianca.meetmewhere

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dianca.meetmewhere.databinding.ItemEventBinding

class EventAdapter(
    private var events: List<EventsEntity>,
    private val onEditClick: (EventsEntity) -> Unit,
    private val onDeleteClick: (EventsEntity) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    // Instead of directly assigning LiveData to events, observe it and update the list
    fun setData(newEvents: List<EventsEntity>) {
        events = newEvents
        notifyDataSetChanged()
    }

    inner class EventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventsEntity) {
            binding.tvEventTitle.text = event.title
            binding.tvEventDescription.text = event.description

            // Edit event
            binding.btnEditEvent.setOnClickListener {
                onEditClick(event)
            }

            // Delete event
            binding.btnDeleteEvent.setOnClickListener {
                onDeleteClick(event)
            }
        }
    }
}
