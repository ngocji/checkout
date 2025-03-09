package com.xxx.checkout.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.AttributeSet
import android.view.View

class RenderBlurView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    private var blurRadius = 0f

    fun setBlurRadius(radius: Float) {
        blurRadius = radius
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            setRenderEffect(
//                RenderEffect.createBlurEffect(
//                    blurRadius,
//                    blurRadius,
//                    Shader.TileMode.DECAL
//                )
//            )
//        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawARGB((blurRadius * 5).toInt().coerceAtMost(100), 255, 255, 255)
    }
}