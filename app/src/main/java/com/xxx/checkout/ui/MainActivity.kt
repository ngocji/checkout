package com.xxx.checkout.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.xxx.checkout.R
import com.xxx.checkout.databinding.ActivityMainBinding
import com.xxx.checkout.ui.fragment.CheckoutFragment
import com.xxx.checkout.utils.FragmentUtils
import com.xxx.checkout.utils.collectState
import com.xxx.checkout.utils.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initFragment()
        initViews()
        observes()
    }

    private fun initFragment() {
        FragmentUtils.replace(
            FragmentUtils.ReplaceOption.with(this)
                .setContainerId(R.id.fragment_container)
                .setFragment(CheckoutFragment())
        )
    }

    private fun initViews() {
        with(binding) {
            toolbar.setNavigationOnClickListener { viewModel.closeCheckout() }
        }
    }

    private fun observes() {
        collectState(viewModel.uiState) { state ->
            with(binding) {
                toolbar.title = state.title
                if (state.isCheckout) {
                    val checkedColor = ContextCompat.getColor(this@MainActivity, R.color.color_checked)
                    binding.toolbar.setBackgroundColor(checkedColor)
                    window.statusBarColor = checkedColor
                }
            }
        }

        collectState(viewModel.closeFlow) {
            // todo end cooldown
            finish()
        }
    }
}