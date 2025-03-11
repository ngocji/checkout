package com.showslinger.fan.ui.dialog

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import com.showslinger.fan.R
import com.showslinger.fan.adapter.EditTicketAdapter.Companion.PAY_CHANGE_QUANTITY
import com.showslinger.fan.adapter.EditTicketDetailAdapter
import com.showslinger.fan.base.BaseBottomSheetDialog
import com.showslinger.fan.databinding.DialogDetailTicketCartBinding
import com.showslinger.fan.model.Ticket
import com.showslinger.fan.utils.PriceFormatter
import com.showslinger.fan.utils.viewBinding

class DetailCartDialog : BaseBottomSheetDialog(R.layout.dialog_detail_ticket_cart) {
    private val binding by viewBinding(DialogDetailTicketCartBinding::bind)

    private var transition: Transition? = null
    private var adapter: EditTicketDetailAdapter? = null
    override fun isEnableDraggable(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        initViews()
        initData()
    }

    private fun initViews() {
        with(binding) {
            btnBack.setOnClickListener {
                result(false)
            }

            btnCheckout.setOnClickListener {
                result(true)
            }
        }
    }

    private fun result(isCheckout: Boolean) {
        val transition = transition ?: return
        val items = adapter?.getItems() ?: emptyList()
        transition.onChanged(items, isCheckout)
        dismissAllowingStateLoss()
    }

    private fun initData() {
        val data = arguments?.getParcelable("data") as? Transition ?: return run {
            dismissAllowingStateLoss()
        }
        transition = data
        adapter = EditTicketDetailAdapter(data.items) {
            calculateTotal(it)
        }
        binding.recyclerview.adapter = adapter
        calculateTotal(data.items)
    }

    private fun calculateTotal(items: List<Ticket>) {
        binding.tvTitle.text = getString(R.string.text_detail_cart, items.size)
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
            items: List<Ticket>,
            onChanged: (items: List<Ticket>, isCheckout: Boolean) -> Unit,
        ): DetailCartDialog {
            return DetailCartDialog().apply {
                arguments = bundleOf("data" to Transition(items, onChanged))
            }
        }
    }

    @kotlinx.android.parcel.Parcelize
    data class Transition(
        val items: List<Ticket>,
        val onChanged: (items: List<Ticket>, isCheckout: Boolean) -> Unit,
    ) : Parcelable
}