package com.showslinger.fan.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object PriceFormatter {
    fun format(amount: Double, currencyCode: String): String {
        return try {
            val currency = Currency.getInstance(currencyCode)
            val locale = getLocaleForCurrency(currencyCode)
            val formatter = NumberFormat.getCurrencyInstance(locale).apply {
                this.currency = currency
            }
            formatter.format(amount)
        } catch (e: IllegalArgumentException) {
            "~"
        }
    }

    fun formatNumber(num: Long): String {
        return NumberFormat.getNumberInstance().format(num)
    }

    private fun getLocaleForCurrency(currencyCode: String): Locale {
        return Locale.getAvailableLocales().firstOrNull {
            try {
                Currency.getInstance(it) == Currency.getInstance(currencyCode)
            } catch (e: Exception) {
                false
            }
        } ?: Locale.getDefault()
    }
}