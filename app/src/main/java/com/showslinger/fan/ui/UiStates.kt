package com.showslinger.fan.ui

import com.showslinger.fan.model.Event
import com.showslinger.fan.model.User
import java.util.concurrent.TimeUnit

data class UiResultState(
    val point: Int,
    val yourPoint: Int,
    val user: User,
    val banner: String,
    val events: List<Event> = emptyList(),
) {
    companion object {
        fun default(): UiResultState {
            return UiResultState(
                point = 100,
                yourPoint = 100,
                banner = "",
                user = User("City Theater", "paulv@showslinger.com", "033 382 2928"),
            )
        }
    }
}

data class UiCheckoutState(
    val title: String,
    val isCheckout: Boolean,
    val cooldown: Long,
    val currencyCode: String = "USD",
    val displayCheckoutEvents: List<Any> = emptyList(),
) {
    companion object {
        fun default() = UiCheckoutState(
            title = "2025 NHRA DEPOSIT...echnology Raceway",
            isCheckout = false,
            cooldown = TimeUnit.MINUTES.toMillis(10),
        )
    }
}