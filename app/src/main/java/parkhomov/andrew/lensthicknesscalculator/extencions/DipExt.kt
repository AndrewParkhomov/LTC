package parkhomov.andrew.lensthicknesscalculator.extencions

import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.Fragment

fun Context.dip(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
fun Fragment.dip(dp: Int): Int = requireContext().dip(dp)