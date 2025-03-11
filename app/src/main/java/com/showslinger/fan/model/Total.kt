package com.showslinger.fan.model

data class Total(
    val currencyCode: String,
    val faceValue: Double = -1.0,
    val subTotal: Double = -1.0,
    val tax: Double = -1.0,
    val total: Double,
    val refund: Boolean = false,
)