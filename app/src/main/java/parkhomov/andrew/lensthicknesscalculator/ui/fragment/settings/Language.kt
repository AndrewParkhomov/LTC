package parkhomov.andrew.lensthicknesscalculator.ui.fragment.settings

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_language.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseDialog
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.buttonId
import parkhomov.andrew.lensthicknesscalculator.utils.localeIso2
import parkhomov.andrew.lensthicknesscalculator.utils.shaderPref
import java.util.*


/**
 * This class is for switch language. Radiobutton checked changed, and locale is update.
 */
class Language : BaseDialog() {

    private var sharedPreferences: SharedPreferences? = null
    private var radioButtonId: Int = -1
    private var languageIso2: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_language, container, false)

        sharedPreferences = activity!!.getSharedPreferences(shaderPref, Context.MODE_PRIVATE)
        radioButtonId = sharedPreferences?.getInt(buttonId, -1)!!
        languageIso2 = sharedPreferences?.getString(localeIso2, "")

        return view
    }

    override fun setUp(view: View) {
        addRadioButtons()
    }

    private fun addRadioButtons() {
        val languages: List<String> = listOf(getString(R.string.english), getString(R.string.russian), getString(R.string.ukrainian))
        radioButtonContainer.orientation = LinearLayout.VERTICAL

        if (radioButtonId == -1 || languageIso2 != "") {
            languageIso2 = Locale.getDefault().language.substring(0, 2)
            when (languageIso2) {
                "uk" -> radioButtonId = 2
                "ru" -> radioButtonId = 1
                else -> {
                    radioButtonId = 0
                    languageIso2 = "en"
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
                    Utils.convertDpToPixel(baseActivity!!, 20.0),
                    Utils.convertDpToPixel(baseActivity!!, 20.0),
                    0,
                    Utils.convertDpToPixel(baseActivity!!, 20.0))
            radioButtonContainer.addView(button)
        }

        // This overrides the radiogroup onCheckListener
        radioButtonContainer.setOnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state
            (group.findViewById(checkedId) as RadioButton).isChecked
            radioButtonId = checkedId
            languageIso2 = when (checkedId) {
                2 -> "uk"
                1 -> "ru"
                else -> "en"
            }
            saveLanguageInSharedPref()
            baseActivity?.recreate()
            dismiss()
        }
    }

    private fun saveLanguageInSharedPref() {
        sharedPreferences?.edit()?.putInt(buttonId, radioButtonId)?.apply()
        sharedPreferences?.edit()?.putString(localeIso2, languageIso2)?.apply()
    }

    companion object {

        val TAG: String = Language::class.java.simpleName
        val instance = Language()
    }
}
