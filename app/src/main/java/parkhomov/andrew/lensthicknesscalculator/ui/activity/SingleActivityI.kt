package parkhomov.andrew.lensthicknesscalculator.ui.activity

import android.support.annotation.StringRes
import parkhomov.andrew.lensthicknesscalculator.base.MvpPresenter
import parkhomov.andrew.lensthicknesscalculator.base.MvpView
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData

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
        fun onRateAppClicked()
        fun onShareResultClicked()
        fun setTestForSharing(sharedText: String)
    }

    interface View : MvpView<Presenter> {
        fun highlightTab(position: Int)
        fun showRateThisAppDialog()
        fun showAboutDialog()
        fun openGooglePlay()

        fun shareResult(sharedText: String)
        fun showToast(@StringRes resId: Int)
        fun createStringForSharing(calculatedData: CalculatedData)
    }

}




