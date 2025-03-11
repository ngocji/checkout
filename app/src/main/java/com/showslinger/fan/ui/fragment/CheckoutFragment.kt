package com.showslinger.fan.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.showslinger.fan.R
import com.showslinger.fan.adapter.EventAdapter
import com.showslinger.fan.base.BaseFragment
import com.showslinger.fan.databinding.FragmentCheckoutBinding
import com.showslinger.fan.model.Event
import com.showslinger.fan.ui.MainViewModel
import com.showslinger.fan.ui.dialog.EditQuantityTicketDialog
import com.showslinger.fan.ui.dialog.OrderProtectionDialog
import com.showslinger.fan.utils.collectState
import com.showslinger.fan.utils.formatTimeMills
import com.showslinger.fan.utils.viewBinding

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