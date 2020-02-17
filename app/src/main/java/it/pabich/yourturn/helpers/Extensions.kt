package it.pabich.yourturn.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import kotlin.math.roundToInt

/**
 * Inflate a [layoutRes] to the view
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Int.toDP(context: Context): Int {
    val density: Float = context.resources.displayMetrics.density
    return (this.toFloat() * density).roundToInt()
}