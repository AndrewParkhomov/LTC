package parkhomov.andrew.language.usecase

import parkhomov.andrew.base.usecase.BaseUseCase
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.utils.appLanguage
import parkhomov.andrew.language.R
import parkhomov.andrew.language.usecase.UseCaseLanguage.Result

class UseCaseLanguageImpl(
        private val preferencesHelper: PreferencesHelper
) : BaseUseCase<Result>(),
        UseCaseLanguage {

    override fun getRadioButtonId() {
        val id = when (preferencesHelper.getStringValue(appLanguage, "en")) {
            "ru" -> R.id.radio_button_rus
            "uk" -> R.id.radio_button_ukr
            else -> R.id.radio_button_eng
        }
        liveData.value = Result.RadioButtonId(id)
    }

    override fun setNewLanguage(language: String) {
        preferencesHelper.putStringValue(appLanguage, language)
    }

}
