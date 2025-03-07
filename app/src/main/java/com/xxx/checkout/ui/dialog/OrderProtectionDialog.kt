package com.xxx.checkout.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.xxx.checkout.R
import com.xxx.checkout.adapter.ProtectionAdapter
import com.xxx.checkout.base.BaseBottomSheetDialog
import com.xxx.checkout.databinding.DialogOrderProtectionBinding
import com.xxx.checkout.ui.MainViewModel
import com.xxx.checkout.utils.PriceFormatter
import com.xxx.checkout.utils.viewBinding

class OrderProtectionDialog : BaseBottomSheetDialog(R.layout.dialog_order_protection) {
    private val binding by viewBinding(DialogOrderProtectionBinding::bind)
    private val viewModel by activityViewModels<MainViewModel>()

    override fun isEnableDraggable(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        initProtectionAdapter()
    }

    private fun initViews() {
        with(binding) {
            tvFee.text = getString(
                R.string.text_format_fee_protection,
                PriceFormatter.format(
                    viewModel.getFees().feeProtection,
                    viewModel.getFees().currencyCode
                )
            )

            lnAdd.setOnClickListener { toggleOption(lnAdd, lnDisable) }

            lnDisable.setOnClickListener { toggleOption(lnDisable, lnAdd) }

            btnContinue.setOnClickListener {
                dismissAllowingStateLoss()
                viewModel.setOrderProtection(lnAdd.isSelected)
                QuestionsDialog.newInstance()
                    .show(parentFragmentManager, null)
            }
        }
    }

    private fun toggleOption(select: View, clear: View) {
        select.isSelected = true
        clear.isSelected = false
        binding.btnContinue.isEnabled = true
    }

    private fun initProtectionAdapter() {
        binding.recyclerviewProtection.adapter = ProtectionAdapter()
    }

    private fun initData() {
        viewModel.getAddOrderProtection()?.run {
            toggleOption(
                if (this) binding.lnAdd else binding.lnDisable,
                if (this) binding.lnDisable else binding.lnAdd
            )
        }
    }

    companion object {
        fun newInstance(): OrderProtectionDialog {
            return OrderProtectionDialog()
        }
    }
}