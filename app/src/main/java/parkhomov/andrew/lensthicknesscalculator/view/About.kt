package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.about.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences

class About : DialogFragment(R.layout.about) {

    private val sp: AppPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setAppVersion()
        setTextViewVisibility()
        setClickListener()
    }

    private fun setAppVersion() {
        val appVersion = getString(R.string.about_version, BuildConfig.VERSION_NAME)
        text_view_version.text = appVersion
    }

    private fun setTextViewVisibility() {
        val currentLanguage = sp.getStringValue(APP_LANGUAGE, "en")
        text_view_help_to_translate.isVisible = currentLanguage == "en"
        text_view_portuguese.isVisible = currentLanguage == "po"
    }

    private fun setClickListener() {
        button.setOnClickListener { dismiss() }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, ABOUT)

    companion object {
        const val ABOUT = "ABOUT"
        val instance = About()
    }
}
