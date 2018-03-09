package parkhomov.andrew.lensthicknesscalculator.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_language.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseDialog
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.const
import java.util.*


/**
 * This class is for switch language. Radiobutton checked changed, and locale is update.
 */
class Language : BaseDialog() {

    private var sharedPreferences: SharedPreferences? = null
    private var radioButtonId: Int? = -1
    private var languageIso2: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_language, container, false)

        sharedPreferences = activity!!.getSharedPreferences(const.SHARED_PREF, Context.MODE_PRIVATE)
        radioButtonId = sharedPreferences!!.getInt(const.RADIO_BUTTON_ID, -1)
        languageIso2 = sharedPreferences!!.getString(const.SAVE_LANGUAGE_ISO2, "")

        return view
    }

    override fun setUp(view: View) {
        addRadioButtons()
    }

    fun showDialog(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    private fun closeDialog() = super.dismissDialog(TAG)

    private fun addRadioButtons() {
        val languages = ArrayList<String>(3)
        languages.add(0, getString(R.string.english))
        languages.add(1, getString(R.string.russian))
        languages.add(2, getString(R.string.ukrainian))

        radioButtonContainer.orientation = LinearLayout.VERTICAL

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
            radioButtonContainer.addView(button)
        }

        // This overrides the radiogroup onCheckListener
        radioButtonContainer.setOnCheckedChangeListener { group, checkedId ->
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
            baseActivity?.changeLanguage()
        }
    }

    private fun saveLanguageInSharedPref() {
        sharedPreferences?.edit()?.putInt(const.RADIO_BUTTON_ID, radioButtonId!!)?.apply()
        sharedPreferences?.edit()?.putString(const.SAVE_LANGUAGE_ISO2, languageIso2)?.apply()
    }

    companion object {

        val TAG: String = Language::class.java.simpleName
        val instance = Language()
    }
}
