package com.showslinger.fan.ui.dialog

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import com.showslinger.fan.R
import com.showslinger.fan.adapter.EditTicketAdapter
import com.showslinger.fan.adapter.EditTicketAdapter.Companion.PAY_CHANGE_QUANTITY
import com.showslinger.fan.base.BaseBottomSheetDialog
import com.showslinger.fan.databinding.DialogEditTicketQuantityBinding
import com.showslinger.fan.model.Event
import com.showslinger.fan.model.Ticket
import com.showslinger.fan.utils.PriceFormatter
import com.showslinger.fan.utils.viewBinding

class EditQuantityTicketDialog : BaseBottomSheetDialog(R.layout.dialog_edit_ticket_quantity) {
    private val binding by viewBinding(DialogEditTicketQuantityBinding::bind)

    private var transition: Transition? = null
    private var adapter: EditTicketAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        with(binding) {
            btnCart.setOnClickListener {
                showDetailCart()
            }

            btnCheckout.setOnClickListener {
                val items = adapter?.getItems() ?: emptyList()
                checkout(items)
            }
        }
    }

    private fun initData() {
        val data = arguments?.getParcelable("data") as? Transition ?: return run {
            dismissAllowingStateLoss()
        }
        transition = data
        adapter = EditTicketAdapter(data.event.tickets, ::onChangeQuantity)
        binding.recyclerview.adapter = adapter
        calculateTotal(data.event.tickets)
    }

    private fun onChangeQuantity(position: Int, item: Ticket, quantity: Int) {
        item.quantity += quantity
        adapter?.notifyItemChanged(position, PAY_CHANGE_QUANTITY)
        calculateTotal(adapter?.getItems() ?: emptyList())
    }

    private fun calculateTotal(items: List<Ticket>) {
        binding.tvCount.text = items.sumOf { it.quantity }.toString()
        val price = if (items.isEmpty()) {
            "0.0"
        } else {
            PriceFormatter.format(
                items.sumOf { it.quantity * it.price },
                items.firstOrNull()?.currencyCode ?: ""
            )
        }
        binding.tvTotal.text = price
    }

    private fun showDetailCart() {
        DetailCartDialog.newInstance(
            items = adapter?.getItems()?.filter { it.quantity > 0 } ?: emptyList(),
            onChanged = { items, isCheckout ->
                val newItems = adapter?.getItems()?.toMutableList() ?: return@newInstance
                newItems.forEach { ticket ->
                    if (!items.any { it.id == ticket.id }) {
                        ticket.quantity = 0
                    }
                }

                if (!isCheckout) {
                    adapter?.updateDataset(newItems, useDiffUtil = false)
                    calculateTotal(newItems)
                } else {
                    checkout(newItems)
                }
            })
            .show(childFragmentManager, null)
    }

    private fun checkout(items: List<Ticket>) {
        val transition = transition ?: return run { dismissAllowingStateLoss() }
        transition.onCheckout(
            transition.event.copy(
                tickets = items
            )
        )
        dismissAllowingStateLoss()
    }

    companion object {
        fun newInstance(
            event: Event,
            onCheckout: (Event) -> Unit
        ): EditQuantityTicketDialog {
            return EditQuantityTicketDialog().apply {
                arguments = bundleOf("data" to Transition(event, onCheckout))
            }
        }
    }

    @kotlinx.android.parcel.Parcelize
    data class Transition(
        val event: Event,
        val onCheckout: (Event) -> Unit
    ) : Parcelable
}