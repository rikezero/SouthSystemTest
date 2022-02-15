package com.example.southsystemtest.ui.eventslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.southsystemtest.data.model.EventListResponseItem
import com.example.southsystemtest.databinding.EventItemBinding
import com.example.southsystemtest.utils.*

class EventListAdapter: ListAdapter<EventListResponseItem, EventListAdapter.EventViewHolder>(
    eventDiff
) {

    var onItemClicked: ((EventListResponseItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class EventViewHolder(
        private val binding: EventItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(eventItem: EventListResponseItem) {
            binding.run {
                eventImage.loadImage(eventItem.image?.formatToHTTPS())
                eventDate.text = eventItem.date?.toSimpleDate(Constants.DATE_TIME_BR_FORMAT)
                eventTitle.text = eventItem.title
                eventClickArea.setOnClickListener {
                    onItemClicked?.invoke(eventItem)
                }
            }
        }
    }
}

internal val eventDiff =
    object : DiffUtil.ItemCallback<EventListResponseItem>() {
        override fun areItemsTheSame(
            oldItem: EventListResponseItem,
            newItem: EventListResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: EventListResponseItem,
            newItem: EventListResponseItem
        ): Boolean {
            return oldItem.date == newItem.date &&
                    oldItem.description == newItem.description &&
                    oldItem.id == newItem.id &&
                    oldItem.image == newItem.image &&
                    oldItem.latitude == newItem.latitude &&
                    oldItem.longitude == newItem.longitude &&
                    oldItem.price == newItem.price &&
                    oldItem.title == newItem.title
        }

    }