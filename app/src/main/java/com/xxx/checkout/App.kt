package com.xxx.checkout

import android.app.Application
import com.stripe.android.PaymentConfiguration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            this,
            "pk_test_40u75fX2XMHKiLL3SqAuXMyZHeUpQ76KiGjHifyHd9MuS2Uo7e6T44PlQnkoNT1x0SZq0Ym44S9LXz5UvdH8em8nx000pcCS6mk",
            ""
        )
    }
}