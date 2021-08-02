package parkhomov.andrew.lensthicknesscalculator.viewmodel

import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.helper.PreferencesHelper
import parkhomov.andrew.lensthicknesscalculator.utils.appLanguage

class ViewModelLanguageImpl(
        private val preferencesHelper: PreferencesHelper
) : ViewModelLanguage() {

    override val state: MutableLiveData<State> = MutableLiveData()

    override fun clearEvents() {
        state.value = null
    }

    override fun setRadioButtons() {
        val id = when (preferencesHelper.getStringValue(appLanguage, "")) {
            "po" -> R.id.radio_button_portuguese
            "ru" -> R.id.radio_button_rus
            "uk" -> R.id.radio_button_ukr
            else -> R.id.radio_button_eng
        }
        state.value = State.LanguageReceived(id)
    }

    override fun setNewLanguage(language: String) {
        preferencesHelper.putStringValue(appLanguage, language)
    }
}
