package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.language


import parkhomov.andrew.lensthicknesscalculator.base.MvpView
import parkhomov.andrew.lensthicknesscalculator.base.MvpPresenter

interface LanguageI {

    interface Presenter : MvpPresenter<View> {
        fun setRadioButton()
        fun saveNewAppLanguage(language: String)


    }

    interface View : MvpView<Presenter> {
        fun checkRadioButton(radioButtonId: Int)


    }

}





