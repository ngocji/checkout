package com.xxx.checkout.repo

import com.xxx.checkout.model.Answer
import com.xxx.checkout.model.Event
import com.xxx.checkout.model.Fees
import com.xxx.checkout.model.PaymentInfo
import com.xxx.checkout.model.Question
import com.xxx.checkout.model.Ticket
import com.xxx.checkout.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Repo {
    suspend fun getEvents(): List<Event> {
        return listOf(
            Event(
                name = "2025 NHRA DEPOSIT",
                description = "World Wide Technology Raceway, Madison, IL",
                date = "Sat, Dec 2 - 2:00 pm",
                location = "2009 Stonebrook Circle\n" +
                        "Mt. Juliet, Tennessee 37122",
                info = "Checkout event description | Entry at 1:00 pm",
                tickets = listOf(
                    Ticket("ADA Seating", 14.6, 14.6, 18.28, 1, "USD"),
                    Ticket("ADA Seating", 14.6, 14.6, 18.28, 1, "USD")
                )
            ),
            Event(
                name = "2025 NHRA DEPOSIT 2",
                description = "World Wide Technology Raceway, Madison, IL",
                date = "Sat, Dec 2 - 2:00 pm",
                location = "2009 Stonebrook Circle\n" +
                        "Mt. Juliet, Tennessee 37122",
                info = "Checkout event description | Entry at 1:00 pm",
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

    suspend fun startPay(
        payEvents: List<Event>,
        addOrderProtection: Boolean?,
        answers: List<Answer>?
    ) = withContext(Dispatchers.IO) {
        PaymentInfo(
            banner = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp",
            user = User("City Theater", "paulv@showslinger.com", "033 382 2928"),
            events = payEvents,
            point = 100,
            yourPoint = 100000
        )
    }
}