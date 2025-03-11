package com.showslinger.fan.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Ticket(
    val name: String,
    val price: Double,
    val fromPrice: Double,
    val toPrice: Double,
    var quantity: Int,
    val currencyCode: String,
    val tax: Double = 0.0,
    val id: String = "id_${Random.nextDouble()}",
): Parcelable