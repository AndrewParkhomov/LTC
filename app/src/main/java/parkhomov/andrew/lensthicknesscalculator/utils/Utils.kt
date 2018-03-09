package parkhomov.andrew.lensthicknesscalculator.utils

import android.content.res.ColorStateList
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ScaleXSpan
import android.util.DisplayMetrics
import android.view.View
import parkhomov.andrew.lensthicknesscalculator.MyApp
import parkhomov.andrew.lensthicknesscalculator.R


/**
 * Created by MyPC on 29.07.2017.
 */

object Utils {

    fun enableWrapper(textInputLayout: TextInputLayout) {
        textInputLayout.isEnabled = true
    }

    fun disableWrapper(textInputLayout: TextInputLayout) {
        textInputLayout.isEnabled = false
    }

    fun highlightEditText(editText: TextInputEditText, textInputLayout: TextInputLayout) {
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext, R.color.red_a_700))
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext, R.color.red_a_700))
    }

    fun disableThicknessField(editText: TextInputEditText, textInputLayout: TextInputLayout) {
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext, R.color.black))
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext, R.color.black))
        editText.alpha = 0.38f
        textInputLayout.alpha = 0.38f
    }

    fun makeNormalEditText(editText: TextInputEditText, textInputLayout: TextInputLayout) {
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext, R.color.black))
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext, R.color.black))
        editText.alpha = 0.87f
        textInputLayout.alpha = 0.87f
    }

    private fun setInputTextLayoutColor(textInputLayout: TextInputLayout, color: Int) {
        try {
            val fDefaultTextColor = TextInputLayout::class.java.getDeclaredField("mDefaultTextColor")
            fDefaultTextColor.isAccessible = true
            fDefaultTextColor.set(textInputLayout, ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(color)))

            val fFocusedTextColor = TextInputLayout::class.java.getDeclaredField("mFocusedTextColor")
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

    fun convertDpToPixel(dp: Double): Int {
        val resources = MyApp.getAppContext.resources
        val metrics = resources.displayMetrics
        return dp.toInt() * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

}
