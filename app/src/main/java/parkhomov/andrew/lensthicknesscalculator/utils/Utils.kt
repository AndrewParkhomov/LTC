package parkhomov.andrew.lensthicknesscalculator.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics


object Utils {

    fun convertDpToPixel(context: Context, dp: Double): Int =
            dp.toInt() * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.getColorFromId(resId: Int): Int = ContextCompat.getColor(this, resId)

fun Context.getDrawableFromId(resId: Int): Drawable = ContextCompat.getDrawable(this, resId)!!