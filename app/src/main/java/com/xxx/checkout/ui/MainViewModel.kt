package com.xxx.checkout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxx.checkout.model.Answer
import com.xxx.checkout.model.Event
import com.xxx.checkout.model.Fees
import com.xxx.checkout.model.Question
import com.xxx.checkout.model.Total
import com.xxx.checkout.repo.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _closeChannel = Channel<Boolean>()
    val closeFlow = _closeChannel.receiveAsFlow()

    private val _coolDownChannel = Channel<Long>()
    val coolDownFlow = _coolDownChannel.receiveAsFlow()

    val uiState = MutableStateFlow(
        UiState.default()
    )

    private var events = mutableListOf<Event>()
    private var fees = Fees()
    private var questions = emptyList<Question>()
    private var addOrderProtection: Boolean? = null
    private var answers: List<Answer>? = null

    init {
        runCoroutine {
            events = Repo.getEvents().toMutableList()
            fees = Repo.getFees()
            questions = Repo.getQuestions()
        }
    }

    fun getEvents() = events

    fun getFees() = fees

    fun getQuestions() = questions

    fun getAddOrderProtection() = addOrderProtection

    fun getAnswers() = answers

    fun checkout(event: Event) {
        runCoroutine {
            val data = uiState.value
            val displayedCheckout = mutableListOf<Any>()
            val events = events.apply {
                val index = indexOfFirst { it.name == event.name }
                if (index != -1) {
                    set(index, event)
                }
            }

            events.forEach { event ->
                val tickets = event.tickets.filter { it.quantity > 0 }
                if (tickets.isNotEmpty()) {
                    displayedCheckout.add(event)
                    displayedCheckout.addAll(tickets)
                }
            }

            // total
            val faceValue = fees.faceValue
            val subTotal = events.sumOf {
                it.tickets.sumOf { ticket -> ticket.quantity * ticket.price }
            }
            val taxes = events.sumOf {
                it.tickets.sumOf { ticket -> ticket.quantity * ticket.tax }
            }

            val total = faceValue + subTotal + taxes

            displayedCheckout.add(
                Total(
                    currencyCode = data.currencyCode,
                    faceValue = faceValue,
                    subTotal = subTotal,
                    tax = taxes,
                    total = total,
                    refund = events.size > 1
                )
            )

            val isCheckout = data.isCheckout
            uiState.tryEmit(
                data.copy(
                    isCheckout = true,
                    displayCheckoutEvents = displayedCheckout
                )
            )

            if (!isCheckout) startCooldown()
        }
    }

    private fun startCooldown() {
        viewModelScope.launch {
            createCooldownFlow(uiState.value.cooldown)
                .collectLatest {
                    _coolDownChannel.send(it)
                    if (it <= 0L) closeCheckout()
                }
        }
    }

    private fun createCooldownFlow(duration: Long): Flow<Long> = flow {
        var timeLeft = duration
        while (timeLeft > 0) {
            emit(timeLeft)
            delay(1000L)
            timeLeft -= 1000L
        }
        emit(0)
    }

    private fun runCoroutine(action: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(context = Dispatchers.IO) {
            action(this)
        }
    }

    fun closeCheckout() {
        runCoroutine {
            _closeChannel.send(true)
        }
    }

    fun setOrderProtection(selected: Boolean) {
        addOrderProtection = selected
    }

    fun setAnswers(answers: List<Answer>) {
        this.answers = answers
    }
}