package parkhomov.andrew.lensthicknesscalculator.utils

import android.content.Context
import android.content.res.ColorStateList
import android.support.annotation.StringRes
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ScaleXSpan
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseActivity


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