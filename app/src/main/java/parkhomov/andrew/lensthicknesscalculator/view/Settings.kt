package parkhomov.andrew.lensthicknesscalculator.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.activity.MainActivity
import parkhomov.andrew.lensthicknesscalculator.databinding.SettingsBinding
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_THEME
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences

class Settings : DialogFragment(R.layout.settings) {

    private val sp: AppPreferences by inject()
    private val binding by viewBinding(SettingsBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRadioButtonLanguage()
        setRadioButtonTheme()
    }

    private fun setRadioButtonLanguage() {
        val currentLanguage = sp.getStringValue(APP_LANGUAGE, "")
        val fullNames = resources.getStringArray(R.array.languages_full_names)
        val codes = resources.getStringArray(R.array.languages_code)

        fullNames.zip(codes).forEach { pair ->
            val radioButton = requireContext().createRadioButton(
                currentLanguage,
                pair.first,
                pair.second
            )
            binding.radioGroupLanguage.addView(radioButton)
        }
    }

    private fun setRadioButtonTheme() {
        val theme = sp.getIntValue(APP_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        val checkButtonId = when (theme) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.buttonDay.id
            AppCompatDelegate.MODE_NIGHT_YES -> binding.buttonNight.id
            else -> binding.buttonAuto.id
        }
        binding.radioGroupTheme.findViewById<RadioButton>(checkButtonId)?.isChecked = true

        binding.radioGroupTheme.setOnCheckedChangeListener { group, checkedId ->
            val checkedButton = group.findViewById<RadioButton>(checkedId)
            if (!checkedButton.isChecked) return@setOnCheckedChangeListener
            val newTheme = when (checkedButton.id) {
                binding.buttonDay.id -> AppCompatDelegate.MODE_NIGHT_NO
                binding.buttonNight.id -> AppCompatDelegate.MODE_NIGHT_YES
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
        val mContext = ContextThemeWrapper(this, R.style.RadioButtonStyle)
        return RadioButton(mContext).apply {
            id = View.generateViewId()
            text = languageFullName
            isChecked = languageCode == currentLanguage
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
            activity?.finish()
            activity?.startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, LANGUAGE)

    companion object {
        private const val LANGUAGE = "LANGUAGE"
        val instance = Settings()
    }
}
