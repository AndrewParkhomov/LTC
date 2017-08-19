package parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup

import java.util.ArrayList
import java.util.Locale

import butterknife.BindView
import butterknife.ButterKnife
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.R2
import parkhomov.andrew.lensthicknesscalculator.activities.interfaces.LanguageChangedI
import parkhomov.andrew.lensthicknesscalculator.activities.main.MainActivity
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils

/**
 * This class is for switch language. Radiobutton checked changed, and locale is update.
 */
class Language : DialogFragment() {

    @BindView(R2.id.radioButtonContainer)
    lateinit var radioGroup: RadioGroup

    private var activity: Activity? = null
    private var myView: View? = null
    private var target: LanguageChangedI? = null
    private var saveLanguage: SharedPreferences? = null

    fun setTarget(mainActivity: MainActivity) {
        this.target = mainActivity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater!!.inflate(R.layout.activity_language, container, false)
        ButterKnife.bind(this, myView!!)
        activity = getActivity()
        saveLanguage = activity!!.getSharedPreferences(CONSTANT.SAVE_LANGUAGE, Context.MODE_PRIVATE)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialog.window != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white)
        }
        addRadioButtons()
        checkedPosition
        return myView
    }

    private fun addRadioButtons() {

        val languages = ArrayList<String>(3)
        languages.add(0, getString(R.string.english))
        languages.add(1, getString(R.string.russian))
        languages.add(2, getString(R.string.ukrainian))

        radioGroup = RadioGroup(activity)
        radioGroup.orientation = LinearLayout.VERTICAL
        val checkedPosition = checkedPosition
        for (i in languages.indices) {
            val button = RadioButton(activity, null, R.attr.radioButtonStyle)
            button.setBackgroundColor(Color.TRANSPARENT)
            button.id = i
            if (i == checkedPosition)
                button.isChecked = true
            button.textSize = 20f
            button.text = languages[i]
            button.setPadding(
                    Utils.convertDpToPixel(40.0),
                    Utils.convertDpToPixel(20.0),
                    Utils.convertDpToPixel(20.0),
                    Utils.convertDpToPixel(20.0))
            radioGroup.addView(button)
        }
        (view!!.findViewById(R.id.radioButtonContainer) as ViewGroup).addView(radioGroup)

        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state
            val checkedRadioButton = group.findViewById(checkedId) as RadioButton
            // This puts the value (true/false) into the variable
            val isChecked = checkedRadioButton.isChecked
            // If the radiobutton that has changed in check state is now checked...
            if (isChecked) {
                val checkedPosition = radiobuttonId

                var language: String? = null
                when (checkedPosition) {
                    0 -> language = "en_gb"
                    1 -> language = "ru_ru"
                    2 -> language = "uk_uk"
                }
                saveLanguage!!.edit().putString(CONSTANT.SAVE_LANGUAGE, language).apply()
                currentLanguage = language
            }
        }
    }

    private val checkedPosition: Int
        get() {
            val checkedButtonId: Int
            when (saveLanguage!!.getString(CONSTANT.SAVE_LANGUAGE, "en_gb")) {
                "ru_ru", "ru" -> checkedButtonId = 1
                "uk_uk", "uk" -> checkedButtonId = 2
                else -> checkedButtonId = 0
            }
            return checkedButtonId
        }

    private val radiobuttonId: Int
        get() {
            val radioButton = radioGroup.getChildAt(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.checkedRadioButtonId))) as RadioButton
            return radioButton.id ?: -1
        }

    private //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //                Log.d(Tag.AUTHORIZATION, " createConfigurationContext");
            //                createConfigurationContext(config);
            //            } else {
            //                Log.d(Tag.AUTHORIZATION, " updateConfiguration");

    var currentLanguage: String?
        get() = Locale.getDefault().isO3Language.substring(0, 2)
        set(language) {
            val config = resources.configuration
            val sysLocale: Locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sysLocale = config.locales.get(0)
            } else {
                sysLocale = config.locale
            }
            if (language != null && language != "" && sysLocale.language != language) {
                val locale = Locale(language)

                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    config.setLocale(locale)
                } else {
                    config.locale = locale
                }
                resources.updateConfiguration(config, resources.displayMetrics)
                target!!.languageChanged()
            }
        }

    companion object {

        fun getInstance(target: MainActivity): Language {
            val bundle = Bundle()
            val language = Language()
            language.arguments = bundle
            language.setTarget(target)
            return language
        }
    }
}
