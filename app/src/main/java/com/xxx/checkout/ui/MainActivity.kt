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
        observes()
    }

    private fun initFragment() {
        FragmentUtils.replace(
            FragmentUtils.ReplaceOption.with(this)
                .setContainerId(R.id.fragment_container)
                .setFragment(CheckoutFragment())
        )
    }

    private fun observes() {
        collectState(viewModel.uiState) {
            binding.toolbar.title = it.title
            if (it.isChecked) {
                val checkedColor = ContextCompat.getColor(this, R.color.color_checked)
                binding.toolbar.setBackgroundColor(checkedColor)
                window.statusBarColor = checkedColor
            }
        }
    }
}