package parkhomov.andrew.ltc.extencions

import android.content.res.Resources

fun dip(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()