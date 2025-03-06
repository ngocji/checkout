package com.xxx.checkout.ui.dialog

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import com.xxx.checkout.R
import com.xxx.checkout.adapter.CheckoutAdapter
import com.xxx.checkout.adapter.CheckoutAdapter.Companion.PAY_CHANGE_QUANTITY
import com.xxx.checkout.base.BaseBottomSheetDialog
import com.xxx.checkout.databinding.DialogEditQuantityBinding
import com.xxx.checkout.model.CheckoutItem
import com.xxx.checkout.utils.PriceFormatter
import com.xxx.checkout.utils.viewBinding
import kotlinx.parcelize.Parcelize

class EditQuantityDialog : BaseBottomSheetDialog(R.layout.dialog_edit_quantity) {
    private val binding by viewBinding(DialogEditQuantityBinding::bind)

    private var transition: Transition? = null
    private var adapter: CheckoutAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        with(binding) {
            btnCheckout.setOnClickListener {
                val items = adapter?.getItems() ?: emptyList()
                transition?.onCheckout?.invoke(items)
                dismissAllowingStateLoss()
            }
        }
    }

    private fun initData() {
        val data = arguments?.getParcelable("data") as? Transition ?: return run {
            dismissAllowingStateLoss()
        }
        transition = data
        adapter = CheckoutAdapter(data.items, ::onChangeQuantity)
        binding.recyclerview.adapter = adapter
        binding.tvCount.text = data.items.size.toString()
        binding.tvTotal.text = calculateTotal(data.items)
    }

    private fun onChangeQuantity(position: Int, item: CheckoutItem, quantity: Int) {
        item.quantity += quantity
        adapter?.notifyItemChanged(position, PAY_CHANGE_QUANTITY)
        binding.tvTotal.text = calculateTotal(adapter?.getItems() ?: emptyList())
    }

    private fun calculateTotal(items: List<CheckoutItem>): String {
        if (items.isEmpty()) {
            return PriceFormatter.format(0.0, "USD")
        }

        val totalPrice = items.sumOf { it.quantity * it.price }
        return PriceFormatter.format(totalPrice, items.firstOrNull()?.currencyCode ?: "")
    }


    companion object {
        fun newInstance(
            items: List<CheckoutItem>,
            onCheckout: (List<CheckoutItem>) -> Unit
        ): EditQuantityDialog {
            return EditQuantityDialog().apply {
                arguments = bundleOf("data" to Transition(items, onCheckout))
            }
        }
    }

    @Parcelize
    data class Transition(
        val items: List<CheckoutItem>,
        val onCheckout: (List<CheckoutItem>) -> Unit
    ) : Parcelable
}