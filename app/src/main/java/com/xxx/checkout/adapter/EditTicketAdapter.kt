package com.xxx.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xxx.checkout.R
import com.xxx.checkout.base.BaseAdapter
import com.xxx.checkout.databinding.ItemEditTicketBinding
import com.xxx.checkout.model.Ticket
import com.xxx.checkout.utils.PriceFormatter
import com.xxx.checkout.utils.formatWithLeadingZero

class EditTicketAdapter(
    items: List<Ticket>,
    private val onChangeQuantity: (Int, Ticket, Int) -> Unit,
) : BaseAdapter<Ticket, EditTicketAdapter.ViewHolder>(items) {
    override fun doCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemEditTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            binding.btnAdd.setOnClickListener {
                onChangeQuantity(
                    adapterPosition,
                    getItem(adapterPosition) ?: return@setOnClickListener,
                    1
                )
            }

            binding.btnMinus.setOnClickListener {
                onChangeQuantity(
                    adapterPosition,
                    getItem(adapterPosition) ?: return@setOnClickListener,
                    -1
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            payloads.forEach { obj ->
                if (obj == PAY_CHANGE_QUANTITY) {
                    holder.binding.editQuantity.setText(
                        (getItem(holder.adapterPosition)?.quantity ?: 0).formatWithLeadingZero()
                    )
                }
            }
            return
        }
        super.onBindViewHolder(holder, position, payloads)
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
            editQuantity.setText(item.quantity.formatWithLeadingZero())
        }
    }

    class ViewHolder(val binding: ItemEditTicketBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val PAY_CHANGE_QUANTITY = "PAY_CHANGE_QUANTITY"
    }
}