package com.xxx.checkout.utils

fun Int.formatWithLeadingZero(): String {
    if (this == 0) return "0"
    return "%02d".format(this)
}