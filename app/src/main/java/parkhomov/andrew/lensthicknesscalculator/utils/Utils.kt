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


    fun highlightEditText(context: Context, editText: TextInputEditText, textInputLayout: TextInputLayout) {
        editText.setTextColor(ContextCompat.getColor(context, R.color.red_a_700))
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(context, R.color.red_a_700))
    }

    fun disableThicknessField(context: Context, editText: TextInputEditText, textInputLayout: TextInputLayout) {
        editText.setTextColor(ContextCompat.getColor(context, R.color.black))
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(context, R.color.black))
        editText.alpha = 0.38f
        textInputLayout.alpha = 0.38f
    }

    fun makeNormalEditText(context: Context, editText: TextInputEditText, textInputLayout: TextInputLayout) {
        editText.setTextColor(ContextCompat.getColor(context, R.color.black))
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(context, R.color.black))
        editText.alpha = 0.87f
        textInputLayout.alpha = 0.87f
    }

    private fun setInputTextLayoutColor(textInputLayout: TextInputLayout, color: Int) {
        try {
            val fDefaultTextColor = TextInputLayout::class.java.getDeclaredField("defaultHintTextColor")
            fDefaultTextColor.isAccessible = true
            fDefaultTextColor.set(textInputLayout, ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(color)))

            val fFocusedTextColor = TextInputLayout::class.java.getDeclaredField("focusedTextColor")
            fFocusedTextColor.isAccessible = true
            fFocusedTextColor.set(textInputLayout, ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(color)))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun spacing(src: CharSequence?, kerning: Float): Spannable? {
        if (src == null) return null
        val srcLength = src.length
        if (srcLength < 2)
            return src as? Spannable ?: SpannableString(src)

        val nonBreakingSpace = "\u00A0"
        val builder = src as? SpannableStringBuilder ?: SpannableStringBuilder(src)
        for (i in src.length - 1 downTo 1) {
            builder.insert(i, nonBreakingSpace)
            builder.setSpan(ScaleXSpan(kerning), i, i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return builder
    }

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