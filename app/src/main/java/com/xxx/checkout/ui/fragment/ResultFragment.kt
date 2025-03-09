package com.xxx.checkout.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.xxx.checkout.R
import com.xxx.checkout.adapter.ResultEventAdapter
import com.xxx.checkout.base.BaseFragment
import com.xxx.checkout.databinding.FragmentResultBinding
import com.xxx.checkout.ui.MainViewModel
import com.xxx.checkout.utils.ImageLoader
import com.xxx.checkout.utils.PriceFormatter
import com.xxx.checkout.utils.collectState
import com.xxx.checkout.utils.viewBinding

class ResultFragment : BaseFragment(R.layout.fragment_result) {
    private val binding by viewBinding(FragmentResultBinding::bind)
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observes()
    }

    private fun initViews() {
        with(binding) {
            scrollView.viewTreeObserver.addOnScrollChangedListener {
                val scrollY = scrollView.scrollY
                val blurRadius = (scrollY / 10).coerceAtMost(25).toFloat() // Giới hạn blur max = 25
                blurView.setBlurRadius(blurRadius)
            }

            btnMoreEvent.setOnClickListener {

            }

            btnPrint.setOnClickListener {

            }
        }
    }

    private fun observes() {
        collectState(viewModel.uiResultState) { state ->

            with(binding) {
                ImageLoader.load(imageBanner, state.banner)
                recyclerviewEvents.adapter = ResultEventAdapter(state.events)
                tvUser.text = state.user.name
                tvEmail.text = state.user.email
                tvPhone.text = state.user.phone
                tvPoint.text = PriceFormatter.formatNumber(state.point.toLong())
                tvTotalPoint.text = PriceFormatter.formatNumber(state.yourPoint.toLong())
            }
        }
    }
}