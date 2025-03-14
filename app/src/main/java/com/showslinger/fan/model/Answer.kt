package com.showslinger.fan.model

import android.net.Uri

data class Answer(
    val question: Question,
    val answer: List<String>,
    val fileUri: Uri? = null
)