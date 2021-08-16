package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.databinding.AboutBinding
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences

class About : DialogFragment(R.layout.about) {

    private val sp: AppPreferences by inject()
    private val binding by viewBinding(AboutBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setAppVersion()
        setTextViewVisibility()
        setClickListener()
    }

    private fun setAppVersion() {
        val appVersion = getString(R.string.about_version, BuildConfig.VERSION_NAME)
        binding.textViewVersion.text = appVersion
    }

    private fun setTextViewVisibility() {
        val currentLanguage = sp.getStringValue(APP_LANGUAGE, "en")
        binding.textViewHelpToTranslate.isVisible = currentLanguage == "en"
        binding.textViewPortuguese.isVisible = currentLanguage == "po"
    }

    private fun setClickListener() {
        binding.button.setOnClickListener { dismiss() }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, ABOUT)

    companion object {
        const val ABOUT = "ABOUT"
        val instance = About()
    }
}
