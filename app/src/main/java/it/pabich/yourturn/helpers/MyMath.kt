package it.pabich.yourturn.helpers

import android.content.Context
import kotlin.math.roundToInt

class MyMath {

    companion object {
        fun dpToPx(dp: Int, context: Context): Int {
            val density: Float = context.resources.displayMetrics.density
            return (dp.toFloat() * density).roundToInt()
        }
    }
}