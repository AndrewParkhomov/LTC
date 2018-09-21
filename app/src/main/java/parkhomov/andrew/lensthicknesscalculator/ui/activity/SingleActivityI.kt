package parkhomov.andrew.lensthicknesscalculator.ui.activity

import parkhomov.andrew.lensthicknesscalculator.base.MvpPresenter
import parkhomov.andrew.lensthicknesscalculator.base.MvpView

interface SingleActivityI {

    interface Presenter : MvpPresenter<View> {
        fun onLanguageClicked()
        fun onBackPressed()
        fun onThicknessClicked()
        fun onDiameterClicked()
        fun onTranspositionClicked()
        fun onRateThisAppClicked()
        fun onAboutClicked()
        fun onGlossaryClicked()
    }

    interface View : MvpView<Presenter> {
        fun highlightTab(position: Int)
        fun showRateThisAppDialog()
        fun showLanguageDialog()
        fun showAboutDialog()

    }

}




