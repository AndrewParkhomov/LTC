package parkhomov.andrew.lensthicknesscalculator.activities.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ScaleXSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.main.MyApp;

/**
 * Created by MyPC on 29.07.2017.
 */

public class Utils {

    public static void enableEditTextField(EditText editText){
        editText.setEnabled(true);
    }

    public static void disableEditTextField(EditText editText){
        editText.setEnabled(false);
    }

    public static void enableWrapper(TextInputLayout textInputLayout){
        textInputLayout.setEnabled(true);
    }

    public static void disableWrapper(TextInputLayout textInputLayout){
        textInputLayout.setEnabled(false);
    }

    public static void highlightEditText(EditText editText, TextInputLayout textInputLayout){
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext(), R.color.red_a_700));
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext(), R.color.red_a_700));
    }

    public static void disableThicknessField(EditText editText, TextInputLayout textInputLayout){
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext(), R.color.black));
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext(), R.color.black));
        editText.setAlpha(0.38f);
        textInputLayout.setAlpha(0.38f);
    }

    public static void makeNormalEditText(EditText editText, TextInputLayout textInputLayout){
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext(), R.color.black));
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext(), R.color.black));
        editText.setAlpha(0.87f);
        textInputLayout.setAlpha(0.87f);
    }

    private static void setInputTextLayoutColor(TextInputLayout textInputLayout, int color) {
        try {
            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(textInputLayout, new ColorStateList(new int[][]{{0}}, new int[]{ color }));

            Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
            fFocusedTextColor.setAccessible(true);
            fFocusedTextColor.set(textInputLayout, new ColorStateList(new int[][]{{0}}, new int[]{ color }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Spannable spacing(CharSequence src, float kerning) {
        if (src == null) return null;
        final int srcLength = src.length();
        if (srcLength < 2) return src instanceof Spannable
                ? (Spannable) src
                : new SpannableString(src);

        final String nonBreakingSpace = "\u00A0";
        final SpannableStringBuilder builder = src instanceof SpannableStringBuilder
                ? (SpannableStringBuilder) src
                : new SpannableStringBuilder(src);
        for (int i = src.length() - 1; i >= 1; i--) {
            builder.insert(i, nonBreakingSpace);
            builder.setSpan(new ScaleXSpan(kerning), i, i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return builder;
    }

    public static int convertDpToPixel(double dp) {
        Resources resources = MyApp.getAppContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static class NoPageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {
            if (position < 0) {
                view.setScrollX((int)((float)(view.getWidth()) * position));
            } else if (position > 0) {
                view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
            } else {
                view.setScrollX(0);
            }
        }
    }

    public static InputMethodManager getInputManager() {
        return (InputMethodManager) MyApp.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

}
