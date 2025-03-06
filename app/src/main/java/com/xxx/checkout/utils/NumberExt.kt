package com.xxx.checkout.utils

fun Int.formatWithLeadingZero(): String {
    return "%02d".format(this)
}