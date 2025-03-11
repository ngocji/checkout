package com.showslinger.fan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showslinger.fan.base.BaseAdapter
import com.showslinger.fan.databinding.ItemResultEventBinding
import com.showslinger.fan.model.Event

class ResultEventAdapter(items: List<Event>) : BaseAdapter<Event, ResultEventAdapter.EventViewHolder>(items) {
    override fun doCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(ItemResultEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        with(holder.binding) {
            val event = getItem(position)
            tvName.text = event?.name
            tvDesc.text = event?.description
            tvDate.text = event?.date
            tvLocation.text = event?.location
            tvInfo.text = event?.info
        }
    }

    class EventViewHolder(val binding: ItemResultEventBinding) :
        RecyclerView.ViewHolder(binding.root)
}