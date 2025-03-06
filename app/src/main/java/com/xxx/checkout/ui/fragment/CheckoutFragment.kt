package com.xxx.checkout.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.xxx.checkout.R
import com.xxx.checkout.base.BaseFragment
import com.xxx.checkout.databinding.FragmentCheckoutBinding
import com.xxx.checkout.model.CheckoutItem
import com.xxx.checkout.ui.MainViewModel
import com.xxx.checkout.ui.UiState
import com.xxx.checkout.ui.dialog.EditQuantityDialog
import com.xxx.checkout.utils.collectState
import com.xxx.checkout.utils.toDateFormat
import com.xxx.checkout.utils.viewBinding

class CheckoutFragment : BaseFragment(R.layout.fragment_checkout) {
    private val binding by viewBinding(FragmentCheckoutBinding::bind)
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observes()
    }

    private fun initViews() {
        with(binding) {
            btnTime.setOnClickListener { showEditQuantityDialog(viewModel.uiState.value) }
        }
    }

    private fun observes() {
        collectState(viewModel.uiState) { state ->
            updateTime(state.time, state.isChecked)
            if (state.isChecked) {
                initAdapter(state.items)
            } else {
                showEditQuantityDialog(state)
            }
        }
    }

    private fun updateTime(time: Long, isChecked: Boolean) {
        with(binding) {
            val bgColor = ContextCompat.getColor(
                requireContext(),
                if (isChecked) R.color.transparent else R.color.color_checked
            )
            btnTime.text = time.toDateFormat("HH:mm")
            btnTime.backgroundTintList = ColorStateList.valueOf(bgColor)
            btnTime.setIconResource(if (isChecked) 0 else R.drawable.ic_clock)

            btnClose.isVisible = !isChecked
        }
    }

    private fun initAdapter(items: List<CheckoutItem>) {}

    private fun showEditQuantityDialog(state: UiState) {
        val dialog = EditQuantityDialog.newInstance(state.items) { newItems ->
            viewModel.updateItems(newItems)
        }
        dialog.show(childFragmentManager, null)
    }
}