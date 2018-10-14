package parkhomov.andrew.lensthicknesscalculator.ui.activity

import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BasePresenter
import parkhomov.andrew.lensthicknesscalculator.utils.interactor.Interactor
import javax.inject.Inject

class SingleActivityPresenter
@Inject
constructor(
        private val interactor: Interactor
) : BasePresenter<SingleActivityI.View>(),
        SingleActivityI.Presenter {


    override fun initViews() {
        mvpView?.initViews()
        mvpView?.selectTab(interactor.selectedTabId)
        mvpView?.selectTabPosition(interactor.position)
    }

    override fun selectTab(tabId: String, position: Int) {
        interactor.selectedTabId = tabId
        interactor.position = position
        mvpView?.selectTab(interactor.selectedTabId)
        mvpView?.selectTabPosition(interactor.position)
    }

    override fun onRateThisAppClicked() {
        mvpView?.showRateThisAppDialog()
    }

    override fun onAboutClicked() {
        mvpView?.showAboutDialog()
    }

    override fun onLanguageClicked() {
        mvpView?.showLanguageDialog()
    }

    override fun onRateAppClicked() {
        mvpView?.openGooglePlay()
    }

    override fun onShareResultClicked() {
        if (interactor.calculatedData != null) {
            mvpView?.createStringForSharing(interactor.calculatedData!!)
        } else {
            mvpView?.showSnackbar(R.string.share_result_is_empty)
        }
    }

    override fun setTextForSharing(sharedText: String) {
        mvpView?.shareResult(sharedText)
    }
}
