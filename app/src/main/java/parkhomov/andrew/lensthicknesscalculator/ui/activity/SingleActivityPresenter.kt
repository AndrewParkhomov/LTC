package parkhomov.andrew.lensthicknesscalculator.ui.activity

import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BasePresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.Diameter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.Glossary
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.Thickness
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.Transposition
import parkhomov.andrew.lensthicknesscalculator.utils.diameter
import parkhomov.andrew.lensthicknesscalculator.utils.glossary
import parkhomov.andrew.lensthicknesscalculator.utils.interactor.Interactor
import parkhomov.andrew.lensthicknesscalculator.utils.thickness
import parkhomov.andrew.lensthicknesscalculator.utils.transposition
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SingleActivityPresenter
@Inject
constructor(
        private val router: Router,
        private val interactor: Interactor
) : BasePresenter<SingleActivityI.View>(), SingleActivityI.Presenter {

    override fun onThicknessClicked() {
        mvpView?.highlightTab(thickness)
        router.replaceScreen(Thickness.TAG)
    }

    override fun onDiameterClicked() {
        mvpView?.highlightTab(diameter)
        router.replaceScreen(Diameter.TAG)
    }

    override fun onTranspositionClicked() {
        mvpView?.highlightTab(transposition)
        router.replaceScreen(Transposition.TAG)
    }

    override fun onGlossaryClicked() {
        mvpView?.highlightTab(glossary)
        router.replaceScreen(Glossary.TAG)
    }

    override fun onBackPressed() {

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
        val sharedText = if(interactor.calculatedData != null){
            interactor.calculatedData!!.thicknessCenter + " " +
                    interactor.calculatedData!!.thicknessEdge
        }else{
            ""
        }

        if(sharedText.isNotEmpty()){
            mvpView?.shareResult(sharedText)
        }else{
            mvpView?.showToast(R.string.share_result_is_empty)
        }
    }
}
