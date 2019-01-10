package parkhomov.andrew.language.domain

import parkhomov.andrew.base.domain.BaseUseCase
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.utils.appLanguage
import parkhomov.andrew.language.R
import parkhomov.andrew.language.domain.FetchMemesUseCase.Result
import javax.inject.Inject

class FetchMemesUseCaseImpl @Inject constructor(
        private val preferencesHelper: PreferencesHelper
) : BaseUseCase<Result>(),
        FetchMemesUseCase {

    override fun setRadioButtons() {
        val radioButtonId = when (preferencesHelper.getStringValue(appLanguage, "en")) {
            "ru" -> R.id.radio_button_rus
            "uk" -> R.id.radio_button_ukr
            else -> R.id.radio_button_eng
        }
        liveData.value = FetchMemesUseCase.Result.CheckLanguageRadioButton(radioButtonId)
    }

    override fun setNewLanguage(language: String) {
        preferencesHelper.putStringValue(appLanguage, language)
    }

}
