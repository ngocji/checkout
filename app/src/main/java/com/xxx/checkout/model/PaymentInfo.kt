package com.xxx.checkout.model

data class PaymentInfo(
    val banner: String,
    val user: User,
    val events: List<Event>,
    val point: Int,
    val yourPoint: Int
)