package com.xxx.checkout

import android.app.Application
import com.stripe.android.PaymentConfiguration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
//        PaymentConfiguration.init(
//            this,
//            "",
//            ""
//        )
    }
}