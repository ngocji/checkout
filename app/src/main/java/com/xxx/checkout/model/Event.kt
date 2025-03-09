package com.xxx.checkout.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val name: String,
    val description: String,
    val date: String,
    val location: String,
    val info: String,
    val tickets: List<Ticket>
) : Parcelable