package parkhomov.andrew.lensthicknesscalculator.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.settings.*
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.extencions.dip
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_THEME
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences

class Settings : DialogFragment(R.layout.settings) {

    private val sp: AppPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRadioButtonLanguage()
        setRadioButtonTheme()
    }

    private fun setRadioButtonLanguage() {
        val currentLanguage = sp.getStringValue(APP_LANGUAGE, "en")
        val fullNames = resources.getStringArray(R.array.languages_full_names)
        val codes = resources.getStringArray(R.array.languages_code)

        fullNames.zip(codes).forEach { pair ->
            val radioButton = requireContext().createRadioButton(
                currentLanguage,
                pair.first,
                pair.second
            )
            radio_group_language.addView(radioButton)
        }
    }

    private fun setRadioButtonTheme() {
        val theme = sp.getIntValue(APP_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        val checkButtonId = when (theme) {
            AppCompatDelegate.MODE_NIGHT_NO -> button_day.id
            AppCompatDelegate.MODE_NIGHT_YES -> button_night.id
            else -> button_auto.id
        }
        radio_group_theme.findViewById<RadioButton>(checkButtonId)?.isChecked = true

        radio_group_theme.setOnCheckedChangeListener { group, checkedId ->
            val checkedButton = group.findViewById<RadioButton>(checkedId)
            if (!checkedButton.isChecked) return@setOnCheckedChangeListener
            val newTheme = when (checkedButton.id) {
                button_day.id -> AppCompatDelegate.MODE_NIGHT_NO
                button_night.id -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            saveTheme(newTheme)
        }
    }

    private fun Context.createRadioButton(
        currentLanguage: String,
        languageFullName: String,
        languageCode: String
    ): RadioButton {
        return RadioButton(this).apply {
            id = View.generateViewId()
            background = null
            text = languageFullName
            textSize = 24f
            isChecked = languageCode == currentLanguage
            setPadding(dip(10), dip(12), dip(10), dip(12))
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    saveLanguage(languageCode)
                    applyNewLanguage()
                }
            }
        }
    }

    private fun saveLanguage(newLanguage: String) {
        sp.putStringValue(APP_LANGUAGE, newLanguage)
    }

    private fun saveTheme(newTheme: Int) {
        sp.putIntValue(APP_THEME, newTheme)
        AppCompatDelegate.setDefaultNightMode(newTheme)
    }

    private fun applyNewLanguage() {
        lifecycleScope.launchWhenStarted {
            delay(500) // wait for radioubutton animation end
            activity?.recreate()
            dismiss()
        }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, LANGUAGE)

    companion object {
        private const val LANGUAGE = "LANGUAGE"
        val instance = Settings()
    }
}
