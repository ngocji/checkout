package com.xxx.checkout.repo

import com.xxx.checkout.model.Event
import com.xxx.checkout.model.Fees
import com.xxx.checkout.model.Ticket

object Repo {
    fun getEvents(): List<Event> {
        return listOf(
            Event(
                name = "2025 NHRA DEPOSIT",
                description = "World Wide Technology Raceway, Madison, IL",
                tickets = listOf(
                    Ticket("ADA Seating", 14.6, 14.6, 18.28, 1, "USD"),
                    Ticket("ADA Seating", 14.6, 14.6, 18.28, 1, "USD")
                )
            ),
            Event(
                name = "2025 NHRA DEPOSIT 2",
                description = "World Wide Technology Raceway, Madison, IL",
                tickets = listOf(
                    Ticket("ADA Seating", 14.6, 14.6, 18.28, 1, "USD"),
                    Ticket("ADA Seating", 14.6, 14.6, 18.28, 1, "USD")
                )
            )
        )
    }

    fun getFees(): Fees {
        return Fees()
    }
}