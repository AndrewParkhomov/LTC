package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.language

import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BasePresenter
import parkhomov.andrew.lensthicknesscalculator.utils.interactor.Interactor
import javax.inject.Inject

class LanguagePresenter
@Inject
constructor(
        private val interactor: Interactor
) : BasePresenter<LanguageI.View>(), LanguageI.Presenter {

    override fun setRadioButton() {
        val radioButtonId = when (interactor.getAppLanguage()) {
            "ru" -> R.id.radio_button_rus
            "uk" -> R.id.radio_button_ukr
            else -> R.id.radio_button_eng
        }
        mvpView?.checkRadioButton(radioButtonId)
    }

    override fun saveNewAppLanguage(language: String) {
        interactor.saveNewAppLanguage(language)
    }
}

