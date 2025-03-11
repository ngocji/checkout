package com.showslinger.fan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showslinger.fan.R
import com.showslinger.fan.base.BaseAdapter
import com.showslinger.fan.databinding.ItemEditTicketDetailBinding
import com.showslinger.fan.model.Ticket
import com.showslinger.fan.utils.PriceFormatter

class EditTicketDetailAdapter(
    items: List<Ticket>,
    private val onChanged: (items: List<Ticket>) -> Unit
) : BaseAdapter<Ticket, EditTicketDetailAdapter.ViewHolder>(items) {
    override fun doCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemEditTicketDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            binding.btnDel.setOnClickListener {
                val newItems = getItems().toMutableList()
                newItems.removeAt(absoluteAdapterPosition)
                updateDataset(newItems)
                onChanged(newItems)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        with(holder.binding) {
            tvName.text = item.name
            tvDisplayedPrice.text = holder.itemView.context.getString(
                R.string.text_format_from_to,
                PriceFormatter.format(item.fromPrice, item.currencyCode),
                PriceFormatter.format(item.toPrice, item.currencyCode)
            )
        }
    }

    class ViewHolder(val binding: ItemEditTicketDetailBinding) :
        RecyclerView.ViewHolder(binding.root)
}