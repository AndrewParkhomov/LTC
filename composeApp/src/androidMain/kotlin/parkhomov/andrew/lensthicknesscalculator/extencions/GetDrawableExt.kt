package parkhomov.andrew.lensthicknesscalculator.extencions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getDrawableFromId(resId: Int): Drawable = requireContext().getDrawableFromId(resId)
fun Context.getDrawableFromId(resId: Int): Drawable = ContextCompat.getDrawable(this, resId)!!