package parkhomov.andrew.lensthicknesscalculator.ui.activity

import android.support.annotation.StringRes
import parkhomov.andrew.lensthicknesscalculator.base.MvpPresenter
import parkhomov.andrew.lensthicknesscalculator.base.MvpView
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData

interface SingleActivityI {

    interface Presenter : MvpPresenter<View> {
        fun onLanguageClicked()
        fun onRateThisAppClicked()
        fun onAboutClicked()
        fun onRateAppClicked()
        fun onShareResultClicked()
        fun setTextForSharing(sharedText: String)
        fun initViews()
        fun selectTab(tabId: String, position: Int)
    }

    interface View : MvpView<Presenter> {
        fun showRateThisAppDialog()
        fun showAboutDialog()
        fun openGooglePlay()
        fun shareResult(sharedText: String)
        fun createStringForSharing(calculatedData: CalculatedData)
        fun showLanguageDialog()
        fun initViews()
        fun selectTab(tabId: String)
        fun selectTabPosition(position: Int)
        fun showSnackbar(@StringRes resId: Int)
    }

}




