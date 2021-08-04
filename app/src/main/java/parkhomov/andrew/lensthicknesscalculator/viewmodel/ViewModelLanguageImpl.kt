package parkhomov.andrew.lensthicknesscalculator.viewmodel

import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.helper.PreferencesHelper
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.AppPreferencesHelper.Companion.APP_LANGUAGE

class ViewModelLanguageImpl(
        private val preferencesHelper: PreferencesHelper
) : ViewModelLanguage() {

    override val state: MutableLiveData<State> = MutableLiveData()

    override fun clearEvents() {
        state.value = null
    }

    override fun setRadioButtons() {
        val id = when (preferencesHelper.getStringValue(APP_LANGUAGE, "")) {
            "po" -> R.id.radio_button_portuguese
            "ru" -> R.id.radio_button_rus
            "uk" -> R.id.radio_button_ukr
            else -> R.id.radio_button_eng
        }
        state.value = State.LanguageReceived(id)
    }

    override fun setNewLanguage(language: String) {
        preferencesHelper.putStringValue(APP_LANGUAGE, language)
    }
}
