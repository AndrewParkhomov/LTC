package parkhomov.andrew.language.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.utils.appLanguage
import parkhomov.andrew.language.R

class ViewModelLanguageImpl(
        private val state: MediatorLiveData<State>,
        private val preferencesHelper: PreferencesHelper
) : ViewModelLanguage() {

    override fun getState(): LiveData<State> = state

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
