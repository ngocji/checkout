package com.xxx.checkout.ui

import java.util.concurrent.TimeUnit

data class UiState(
    val title: String,
    val isCheckout: Boolean,
    val cooldown: Long,
    val currencyCode: String = "USD",
    val displayCheckoutEvents: List<Any> = emptyList(),
) {
    companion object {
        fun default() = UiState(
            title = "2025 NHRA DEPOSIT...echnology Raceway",
            isCheckout = false,
            cooldown = TimeUnit.MINUTES.toMillis(10),
        )
    }
}