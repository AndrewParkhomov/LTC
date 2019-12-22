package parkhomov.andrew.language.viewmodel

import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.utils.appLanguage
import parkhomov.andrew.language.R

class ViewModelLanguageImpl(
        private val preferencesHelper: PreferencesHelper
) : ViewModelLanguage() {

    override val state: MutableLiveData<State> = MutableLiveData()

    override fun clearEvents() {
        state.value = null
    }

    override fun setRadioButtons() {
        val id = when (preferencesHelper.getStringValue(appLanguage, "en")) {
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
