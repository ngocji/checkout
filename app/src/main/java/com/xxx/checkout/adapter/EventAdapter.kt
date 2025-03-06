package com.xxx.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.xxx.checkout.base.BaseAdapter
import com.xxx.checkout.databinding.ItemEventBinding
import com.xxx.checkout.databinding.ItemTicketBinding
import com.xxx.checkout.databinding.ItemTotalBinding
import com.xxx.checkout.model.Event
import com.xxx.checkout.model.Ticket
import com.xxx.checkout.model.Total
import com.xxx.checkout.utils.PriceFormatter

class EventAdapter : BaseAdapter<Any, RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Event -> TYPE_EVENT
            is Ticket -> TYPE_TICKET
            is Total -> TYPE_TOTAL
            else -> super.getItemViewType(position)
        }
    }

    override fun doCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EVENT -> {
                val binding =
                    ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EventViewHolder(binding)
            }

            TYPE_TICKET -> {
                val binding =
                    ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TicketViewHolder(binding)
            }

            TYPE_TOTAL -> {
                val binding =
                    ItemTotalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TotalViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        when (holder) {
            is EventViewHolder -> {
                (item as Event).run {
                    holder.binding.spacer.isVisible = position == 0
                    holder.binding.tvName.text = name
                    holder.binding.tvDesc.text = description
                }
            }

            is TicketViewHolder -> {
                (item as Ticket).run {
                    holder.binding.tvName.text = name
                    holder.binding.tvPrice.text =
                        PriceFormatter.format(quantity * price, currencyCode)
                    holder.binding.tvQuantity.text = quantity.toString()
                }
            }

            is TotalViewHolder -> {
                (item as Total).run {
                    holder.binding.tvFaceValue.text = PriceFormatter.format(faceValue, currencyCode)
                    holder.binding.tvFaceValue.isVisible = faceValue > 0

                    holder.binding.tvSubTotal.text = PriceFormatter.format(subTotal, currencyCode)
                    holder.binding.tvSubTotal.isVisible = subTotal > 0

                    holder.binding.tvTax.text = PriceFormatter.format(tax, currencyCode)
                    holder.binding.tvTax.isVisible = tax > 0

                    holder.binding.tvTotal.text = PriceFormatter.format(total, currencyCode)
                    holder.binding.tvRefund.isVisible = refund
                    holder.binding.tvRefundDesc.isVisible = refund
                }
            }
        }
    }

    class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)
    class TicketViewHolder(val binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root)
    class TotalViewHolder(val binding: ItemTotalBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val TYPE_EVENT = 1
        private const val TYPE_TICKET = 2
        private const val TYPE_TOTAL = 3
    }
}