package com.xxx.checkout.ui

import androidx.lifecycle.ViewModel
import com.xxx.checkout.model.CheckoutItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    val uiState = MutableStateFlow(
        UiState.default()
    )

    fun updateItems(newItems: List<CheckoutItem>) {
        uiState.update {
            it.copy(
                isChecked = true,
                items = newItems
            )
        }
    }
}