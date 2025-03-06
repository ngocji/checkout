package com.xxx.checkout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxx.checkout.model.Event
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

    fun checkout(event: Event) {
        runCoroutine {
            // todo create display checkout
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
}