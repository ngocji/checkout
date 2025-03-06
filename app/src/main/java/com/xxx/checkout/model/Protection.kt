package com.xxx.checkout.model

import com.xxx.checkout.R

enum class Protection(val iconRes: Int, val textRes: Int) {
    COVID_19(R.drawable.ic_protection_cv_19, R.string.text_protection_cv_19),
    INJURY(R.drawable.ic_protection_injury, R.string.text_protection_injury),
    SEVERE(R.drawable.ic_protection_severe, R.string.text_protection_severe),
    LAYOFF(R.drawable.ic_protection_layoff, R.string.text_protection_layoff)
}