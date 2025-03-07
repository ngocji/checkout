package com.xxx.checkout.model

data class Question(
    val id: String = "",
    val question: String,
    val type: Type,
    val answers: List<String>? = null
) {
    enum class Type {
        TEXT,
        LARGE_TEXT,
        SINGLE_CHOICE,
        MULTI_CHOICE,
        UPLOAD_DOCUMENT
    }
}