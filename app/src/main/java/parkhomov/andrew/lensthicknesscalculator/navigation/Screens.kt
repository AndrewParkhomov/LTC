package parkhomov.andrew.lensthicknesscalculator.navigation

import androidx.fragment.app.Fragment
import parkhomov.andrew.lensthicknesscalculator.view.Glossary
import parkhomov.andrew.lensthicknesscalculator.view.Diameter
import parkhomov.andrew.lensthicknesscalculator.view.Thickness
import parkhomov.andrew.lensthicknesscalculator.view.Transposition
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object ScreenCompareList : SupportAppScreen() {
        override fun getFragment(): Fragment = parkhomov.andrew.lensthicknesscalculator.view.CompareList.instance
    }

    object ScreenDiameter : SupportAppScreen() {
        override fun getFragment(): Fragment = Diameter.instance
    }
    object ScreenGlossary : SupportAppScreen() {
        override fun getFragment(): Fragment = Glossary.instance
    }
    object ScreenThickness : SupportAppScreen() {
        override fun getFragment(): Fragment = Thickness.instance
    }

    object ScreenTransposition : SupportAppScreen() {
        override fun getFragment(): Fragment = Transposition.instance
    }

}
