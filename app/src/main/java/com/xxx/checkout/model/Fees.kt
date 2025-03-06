package com.xxx.checkout.model

data class Fees(
    val faceValue: Double = 0.0,
    val feeProtection: Double = 0.0,
    val currencyCode: String = "USD"
)