package parkhomov.andrew.lensthicknesscalculator.utils.navigation

import android.support.v4.app.Fragment
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.Diameter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.Glossary
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.Thickness
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.Transposition
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

class Screens {

    class GetBottomTabFragment(private val tabId: String) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return when (tabId) {
                Diameter.TAG -> Diameter.instance
                Transposition.TAG -> Transposition.instance
                Glossary.TAG -> Glossary.instance
                else -> Thickness.instance
            }
        }
    }

}