package parkhomov.andrew.lensthicknesscalculator.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import parkhomov.andrew.lensthicknesscalculator.R


object Utils {

    fun convertDpToPixel(context: Context, dp: Double): Int =
            dp.toInt() * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun FragmentActivity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

fun Context.showMessage(message: String?) {
    Toast.makeText(this, message
            ?: getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
}

fun Context.showMessage(@StringRes resId: Int) {
    showMessage(getString(resId))
}

fun Context.getColorFromId(resId: Int): Int = ContextCompat.getColor(this, resId)

fun Context.getDrawableFromId(resId: Int): Drawable = ContextCompat.getDrawable(this, resId)!!