package com.showslinger.fan.utils

import android.annotation.SuppressLint
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

@SuppressLint("DefaultLocale")
fun Long.formatTimeMills(): String {
    if (this <= 0L) return "00:00"
    val minutes = (this / 1000) / 60
    val seconds = (this / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}