package parkhomov.andrew.lensthicknesscalculator.fragment.settings

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
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
import parkhomov.andrew.lensthicknesscalculator.main.MainActivity
import parkhomov.andrew.lensthicknesscalculator.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import java.util.*


/**
 * This class is for switch language. Radiobutton checked changed, and locale is update.
 */
class Language : DialogFragment() {

    interface LanguageChangedI {
        fun languageChanged()
    }

    @BindView(R.id.radioButtonContainer)
    lateinit var radioGroup: RadioGroup

    private var myView: View? = null
    private var target: LanguageChangedI? = null
    private var sharedPreferences: SharedPreferences? = null
    private var radioButtonId: Int? = -1
    private var languageIso2: String? = null

    fun setTarget(mainActivity: MainActivity) {
        this.target = mainActivity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater!!.inflate(R.layout.activity_language, container, false)
        ButterKnife.bind(this, myView!!)

        sharedPreferences = activity!!.getSharedPreferences(CONSTANT.SHARED_PREF, Context.MODE_PRIVATE)
        radioButtonId = sharedPreferences!!.getInt(CONSTANT.RADIO_BUTTON_ID, -1)
        languageIso2 = sharedPreferences!!.getString(CONSTANT.SAVE_LANGUAGE_ISO2, "")
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

        if (radioButtonId == -1 || languageIso2 != "") {
            languageIso2 = Locale.getDefault().language.substring(0, 2)
            when (languageIso2) {
                "uk" -> radioButtonId = 2
                "ru" -> radioButtonId = 1
                else -> {
                    radioButtonId = 0; languageIso2 = "en"
                }
            }
            saveLanguageInSharedPref()
        }
        for (i in languages.indices) {
            val button = RadioButton(activity, null, R.attr.radioButtonStyle)
            button.setBackgroundColor(Color.TRANSPARENT)
            button.id = i
            if (i == radioButtonId)
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
        (myView!!.findViewById<RadioGroup>(R.id.radioButtonContainer) as ViewGroup).addView(radioGroup)

        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state
            val checkedRadioButton = group.findViewById<RadioButton>(checkedId) as RadioButton
            checkedRadioButton.isChecked
            radioButtonId = checkedId
            languageIso2 = when (checkedId) {
                2 -> "uk"
                1 -> "ru"
                else -> "en"
            }
            saveLanguageInSharedPref()
            target!!.languageChanged()
        }
    }

    private fun saveLanguageInSharedPref() {
        sharedPreferences!!.edit().putInt(CONSTANT.RADIO_BUTTON_ID, radioButtonId!!).apply()
        sharedPreferences!!.edit().putString(CONSTANT.SAVE_LANGUAGE_ISO2, languageIso2).apply()
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
