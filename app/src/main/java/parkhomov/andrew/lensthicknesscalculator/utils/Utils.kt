package parkhomov.andrew.lensthicknesscalculator.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun Context.getColorFromId(resId: Int): Int = ContextCompat.getColor(this, resId)

fun Context.getDrawableFromId(resId: Int): Drawable = ContextCompat.getDrawable(this, resId)!!