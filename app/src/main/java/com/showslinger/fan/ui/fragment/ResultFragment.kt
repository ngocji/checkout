package com.showslinger.fan.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.showslinger.fan.R
import com.showslinger.fan.adapter.ResultEventAdapter
import com.showslinger.fan.base.BaseFragment
import com.showslinger.fan.databinding.FragmentResultBinding
import com.showslinger.fan.ui.MainViewModel
import com.showslinger.fan.utils.ImageLoader
import com.showslinger.fan.utils.PriceFormatter
import com.showslinger.fan.utils.collectState
import com.showslinger.fan.utils.viewBinding

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
                val blurRadius = (scrollY / 5).coerceAtMost(25).toFloat()
                blurView.setBlurRadius(blurRadius)
                if (blurRadius <= 0f) {
                    blurView.alpha = 0f
                } else {
                    blurView.alpha = 1f
                }
                Log.e("Blur", "$blurRadius")
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