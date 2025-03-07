package com.xxx.checkout.repo

import com.xxx.checkout.model.Event
import com.xxx.checkout.model.Fees
import com.xxx.checkout.model.Question
import com.xxx.checkout.model.Ticket

object Repo {
    suspend fun getEvents(): List<Event> {
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

    suspend fun getFees(): Fees {
        return Fees()
    }

    suspend fun getQuestions(): List<Question> {
        return listOf(
            Question(
                question = "What is your name?",
                type = Question.Type.TEXT
            ),
            Question(
                question = "What is your name?",
                type = Question.Type.LARGE_TEXT
            ),
            Question(
                question = "What is your name?",
                type = Question.Type.SINGLE_CHOICE,
                answers = listOf("A", "B", "C")
            ),
            Question(
                question = "What is your name?",
                type = Question.Type.MULTI_CHOICE,
                answers = listOf("A", "B", "C")
            ),
        )
    }
}