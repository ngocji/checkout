package com.showslinger.fan.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    val name: String,
    val price: Double,
    val fromPrice: Double,
    val toPrice: Double,
    var quantity: Int,
    val currencyCode: String,
    val tax: Double = 0.0
): Parcelable