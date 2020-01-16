package it.pabich.yourturn.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


class SplashScreen(context: Context) : View(context) {
    private var splashDrawable: Drawable? = null

    fun setSplashDrawable(@DrawableRes drawable: Int) {
        splashDrawable = ContextCompat.getDrawable(context, drawable)
        splashDrawable!!.callback = this
    }

    fun getDrawable() : Drawable? {
        return splashDrawable
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        val windowInsets = rootWindowInsets
        splashDrawable!!.setBounds(
            -windowInsets.systemWindowInsetLeft,
            -windowInsets.systemWindowInsetTop,
            width + windowInsets.systemWindowInsetRight,
            height + windowInsets.systemWindowInsetBottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        splashDrawable!!.draw(canvas)
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return who === splashDrawable || super.verifyDrawable(who)
    }
}