package parkhomov.andrew.lensthicknesscalculator.extencions

import android.content.res.Resources

fun dip(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()