package com.xxx.checkout.ui

import com.xxx.checkout.model.Event
import com.xxx.checkout.model.Ticket
import java.util.concurrent.TimeUnit

data class UiState(
    val title: String,
    val isCheckout: Boolean,
    val cooldown: Long,
    val events: List<Event>,
    val displayCheckoutEvents: List<Any> = emptyList(),
) {
    companion object {
        fun default() = UiState(
            title = "2025 NHRA DEPOSIT...echnology Raceway",
            isCheckout = false,
            cooldown = TimeUnit.MINUTES.toMillis(10),
            events = listOf(
                Event(
                    name = "2025 NHRA DEPOSIT",
                    description = "World Wide Technology Raceway, Madison, IL",
                    tickets = listOf(
                        Ticket("ADA Seating", 14.6, 14.6, 18.28, 0,"USD"),
                        Ticket("ADA Seating", 14.6, 14.6, 18.28, 0,"USD")
                    )
                ),
                Event(
                    name = "2025 NHRA DEPOSIT 2",
                    description = "World Wide Technology Raceway, Madison, IL",
                    tickets = listOf(
                        Ticket("ADA Seating", 14.6, 14.6, 18.28, 0,"USD"),
                        Ticket("ADA Seating", 14.6, 14.6, 18.28, 0,"USD")
                    )
                )
            )
        )
    }
}