package com.xxx.checkout.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {
    fun load(imageView: ImageView, uri: Any?) {
        Glide.with(imageView.context)
            .load(uri)
            .into(imageView)
    }
}