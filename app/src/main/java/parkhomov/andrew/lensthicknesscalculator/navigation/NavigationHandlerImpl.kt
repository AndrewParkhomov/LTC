package parkhomov.andrew.lensthicknesscalculator.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.helper.NavigationI
import parkhomov.andrew.base.utils.diameter
import parkhomov.andrew.base.utils.thickness
import parkhomov.andrew.base.utils.transposition
import parkhomov.andrew.language.view.Language

class NavigationHandlerImpl(

) : NavigationI {

    override fun getSelectedFragment(position: Int): Fragment {
        return when (position) {
            thickness -> parkhomov.andrew.thickness.navigation.Screens.ScreenThickness.fragment
            diameter -> parkhomov.andrew.diameter.navigation.Screens.ScreenDiameter.fragment
            transposition -> parkhomov.andrew.transposition.navigation.Screens.ScreenTransposition.fragment
            else -> parkhomov.andrew.glossary.navigation.Screens.ScreenGlossary.fragment
        }
    }

    override fun showLanguageDialog(supportFragmentManager: FragmentManager) {
        Language.instance.show(supportFragmentManager)
    }

    override fun showResultDialog(childFragmentManager: FragmentManager, caseThickness: CalculatedData) {
        parkhomov.andrew.result.view.Result.getInstance(caseThickness).show(childFragmentManager)
    }
}