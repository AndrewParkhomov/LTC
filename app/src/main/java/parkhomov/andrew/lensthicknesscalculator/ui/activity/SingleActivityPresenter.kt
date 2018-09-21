package parkhomov.andrew.lensthicknesscalculator.ui.activity

import parkhomov.andrew.lensthicknesscalculator.base.BasePresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.Glossary
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.Thickness
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.Diameter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.Transposition
import parkhomov.andrew.lensthicknesscalculator.utils.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SingleActivityPresenter
@Inject
constructor(
        private val router: Router
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
}
