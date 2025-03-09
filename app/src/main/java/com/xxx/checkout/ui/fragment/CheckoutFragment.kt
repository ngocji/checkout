package com.xxx.checkout.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.xxx.checkout.R
import com.xxx.checkout.adapter.EventAdapter
import com.xxx.checkout.base.BaseFragment
import com.xxx.checkout.databinding.FragmentCheckoutBinding
import com.xxx.checkout.model.Event
import com.xxx.checkout.ui.MainViewModel
import com.xxx.checkout.ui.dialog.EditQuantityTicketDialog
import com.xxx.checkout.ui.dialog.OrderProtectionDialog
import com.xxx.checkout.utils.collectState
import com.xxx.checkout.utils.formatTimeMills
import com.xxx.checkout.utils.viewBinding

class CheckoutFragment : BaseFragment(R.layout.fragment_checkout) {
    private val binding by viewBinding(FragmentCheckoutBinding::bind)
    private val viewModel by activityViewModels<MainViewModel>()
    private val adapter by lazy { EventAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observes()
    }

    private fun initViews() {
        with(binding) {
            btnClose.setOnClickListener {
                viewModel.closeCheckout()
            }

            btnCheckout.setOnClickListener {
                OrderProtectionDialog.newInstance()
                    .show(childFragmentManager, null)
            }
        }
    }

    private fun observes() {
        collectState(viewModel.uiCheckoutState) { state ->
            if (state.isCheckout) {
                initAdapter(state.displayCheckoutEvents)
            } else {
                binding.btnTime.text = state.cooldown.formatTimeMills()
                showEditQuantityDialog(viewModel.getEvents().firstOrNull() ?: return@collectState)
            }
        }

        collectState(viewModel.coolDownFlow) { time ->
            if (time >= 0L) {
                with(binding) {
                    val bgColor = ContextCompat.getColor(requireContext(), R.color.transparent)
                    btnTime.text = time.formatTimeMills()
                    btnTime.backgroundTintList = ColorStateList.valueOf(bgColor)
                    btnTime.setIconResource(0)
                    btnClose.isVisible = false
                }
            }
        }
    }

    private fun initAdapter(items: List<Any>) {
        adapter.updateDataset(items, true)
        binding.recyclerview.adapter = adapter
    }

    // todo show edit quantity
    private fun showEditQuantityDialog(event: Event) {
        val dialog = EditQuantityTicketDialog.newInstance(event) { newEvent ->
            viewModel.checkout(newEvent)
        }
        dialog.show(childFragmentManager, null)
    }
}