package com.xxx.checkout.ui

import com.xxx.checkout.model.CheckoutItem

data class UiState(
    val title: String,
    val isChecked: Boolean,
    val time: Long,
    val items: List<CheckoutItem>
) {
    companion object {
        fun default() = UiState(
            title = "2025 NHRA DEPOSIT...echnology Raceway",
            isChecked = false,
            time = System.currentTimeMillis(),
            listOf(
                CheckoutItem("ADA Seating", 14.6, 14.6, 18.28, 1,"USD"),
                CheckoutItem("ADA Seating", 14.6, 14.6, 18.28, 1,"USD")
            )
        )
    }
}