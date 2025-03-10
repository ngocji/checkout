package com.xxx.checkout.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.PaymentSheetResultCallback
import com.xxx.checkout.R
import com.xxx.checkout.databinding.ActivityMainBinding
import com.xxx.checkout.ui.fragment.CheckoutFragment
import com.xxx.checkout.ui.fragment.ResultFragment
import com.xxx.checkout.utils.FragmentUtils
import com.xxx.checkout.utils.collectState
import com.xxx.checkout.utils.viewBinding

class MainActivity : AppCompatActivity(), PaymentSheetResultCallback {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var paymentSheet: PaymentSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initPaymentSheet()
        initFragment()
        initViews()
        observes()
    }

    override fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {

    }

    private fun initPaymentSheet() {
        paymentSheet = PaymentSheet.Builder(this)
            .build(this)
    }

    private fun initFragment() {
        changeFragment(CheckoutFragment())
    }

    private fun initViews() {
        with(binding) {
            toolbar.setNavigationOnClickListener { viewModel.closeCheckout() }
        }
    }

    private fun observes() {
        collectState(viewModel.uiCheckoutState) { state ->
            with(binding) {
                toolbar.title = state.title
                if (state.isCheckout) {
                    val checkedColor =
                        ContextCompat.getColor(this@MainActivity, R.color.color_checked)
                    binding.toolbar.setBackgroundColor(checkedColor)
                    window.statusBarColor = checkedColor
                }
            }
        }

        collectState(viewModel.closeFlow) {
            finish()
        }

        collectState(viewModel.resultFlow) {
            changeFragment(ResultFragment())
        }

        collectState(viewModel.addPayment) {
            // todo add payment
//            paymentSheet.presentWithPaymentIntent("")
            viewModel.startPay()
        }
    }

    private fun changeFragment(fragment: Fragment) {
        FragmentUtils.replace(
            FragmentUtils.ReplaceOption.with(this)
                .setContainerId(R.id.fragment_container)
                .setFragment(fragment)
        )
    }
}