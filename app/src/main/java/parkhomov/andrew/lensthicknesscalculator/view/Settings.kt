package parkhomov.andrew.lensthicknesscalculator.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
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
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        setRadioButtonLanguage()
        setRadioButtonTheme()
        setAppVersion()

        binding.closeIV.setOnClickListener {
            dismiss()
        }
        binding.textViewEmail.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            val clip = ClipData.newPlainText("developer email", binding.textViewEmail.text)
            clipboard?.setPrimaryClip(clip)
        }
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
                LayoutParams.MATCH_PARENT,
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
        dismiss()
    }

    private fun applyNewLanguage() {
        lifecycleScope.launchWhenStarted {
            delay(500) // wait for radioubutton animation end
            activity?.finish()
            activity?.startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    private fun setAppVersion() {
        val appVersion = getString(R.string.about_version, BuildConfig.VERSION_NAME)
        binding.textViewVersion.text = appVersion
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, SETTINGS)

    companion object {
        private const val SETTINGS = "SETTINGS"
        val instance = Settings()
    }
}
