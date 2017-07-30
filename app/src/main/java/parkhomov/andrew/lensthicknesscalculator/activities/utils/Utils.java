package parkhomov.andrew.lensthicknesscalculator.activities.utils;

import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
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
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext(), R.color.horizontal_divider));
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext(), R.color.horizontal_divider));
    }

    public static void disableThicknessField(EditText editText, TextInputLayout textInputLayout){
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext(), R.color.imageButtonBlue));
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext(), R.color.imageButtonBlue));
    }

    public static void makeNormalEditText(EditText editText, TextInputLayout textInputLayout){
        editText.setTextColor(ContextCompat.getColor(MyApp.getAppContext(), R.color.black));
        setInputTextLayoutColor(textInputLayout, ContextCompat.getColor(MyApp.getAppContext(), R.color.black));
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

}
