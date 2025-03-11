package com.xxx.checkout.ui.dialog

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import com.xxx.checkout.R
import com.xxx.checkout.adapter.EditTicketAdapter
import com.xxx.checkout.adapter.EditTicketAdapter.Companion.PAY_CHANGE_QUANTITY
import com.xxx.checkout.base.BaseBottomSheetDialog
import com.xxx.checkout.databinding.DialogEditTicketQuantityBinding
import com.xxx.checkout.model.Event
import com.xxx.checkout.model.Ticket
import com.xxx.checkout.utils.PriceFormatter
import com.xxx.checkout.utils.viewBinding
import kotlinx.parcelize.Parcelize

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
            btnCheckout.setOnClickListener {
                val transition = transition ?: return@setOnClickListener
                val items = adapter?.getItems() ?: emptyList()
                transition.onCheckout(
                    transition.event.copy(
                        tickets = items
                    )
                )
                dismissAllowingStateLoss()
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