package parkhomov.andrew.lensthicknesscalculator.fragment.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import butterknife.BindView
import butterknife.ButterKnife
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.R2
import parkhomov.andrew.lensthicknesscalculator.interfaces.LanguageChangedI
import parkhomov.andrew.lensthicknesscalculator.main.MainActivity
import parkhomov.andrew.lensthicknesscalculator.main.MyApp
import parkhomov.andrew.lensthicknesscalculator.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import java.util.*



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
        saveLanguage = MyApp.getAppContext.getSharedPreferences(CONSTANT.SAVE_LANGUAGE, Context.MODE_PRIVATE)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialog.window != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white)
        }
        addRadioButtons()

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
        (myView!!.findViewById(R.id.radioButtonContainer) as ViewGroup).addView(radioGroup)

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
                    0 -> language = "en"
                    1 -> language = "ru"
                    2 -> language = "uk"
                }
                saveLanguage!!.edit().putString(CONSTANT.SAVE_LANGUAGE, language).apply()

                if (language != null && language != "") {
                    val locale = Locale(language)
                    val config = resources.configuration
                    Locale.setDefault(locale)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        config.setLocale(locale)
                     else
                        config.locale = locale

                    resources.updateConfiguration(config, resources.displayMetrics)
                    target!!.languageChanged()
                }
            }
        }
    }

    private val checkedPosition: Int
        get() {
            val checkedButtonId: Int
            var language = saveLanguage!!.getString(CONSTANT.SAVE_LANGUAGE, Utils.getCurrentLanguage())
            when (language) {
                "ru_ru", "ru" -> {
                    language = "ru"
                    checkedButtonId = 1
                }
                "uk_uk", "uk" -> {
                    language = "uk"
                    checkedButtonId = 2
                }
                else -> {
                    language = "en"
                    checkedButtonId = 0
                }
            }
            saveLanguage!!.edit().putString(CONSTANT.SAVE_LANGUAGE, language).apply()
            return checkedButtonId
        }

    private val radiobuttonId: Int
        get() {
            val radioButton = radioGroup.getChildAt(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.checkedRadioButtonId))) as RadioButton
            return radioButton.id
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
