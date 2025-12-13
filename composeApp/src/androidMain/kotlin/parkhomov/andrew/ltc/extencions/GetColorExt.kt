package parkhomov.andrew.ltc.extencions

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getColorFromId(resId: Int): Int = requireContext().getColorFromId(resId)
fun Context.getColorFromId(resId: Int): Int = ContextCompat.getColor(this, resId)

