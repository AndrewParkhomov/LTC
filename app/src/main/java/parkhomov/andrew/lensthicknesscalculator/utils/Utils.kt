package parkhomov.andrew.lensthicknesscalculator.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getColorFromId(resId: Int): Int = requireContext().getColorFromId(resId)
fun Context.getColorFromId(resId: Int): Int = ContextCompat.getColor(this, resId)

fun Fragment.getDrawableFromId(resId: Int): Drawable = requireContext().getDrawableFromId(resId)
fun Context.getDrawableFromId(resId: Int): Drawable = ContextCompat.getDrawable(this, resId)!!

fun Context.dip(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
fun Fragment.dip(dp: Int): Int = requireContext().dip(dp)
