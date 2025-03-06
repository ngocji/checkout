package com.xxx.checkout.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long.toDateFormat(pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        val date = Calendar.getInstance().apply {
            timeInMillis = this@toDateFormat
        }
        simpleDateFormat.format(date.time)
    } catch (e: Exception) {
        ""
    }
}