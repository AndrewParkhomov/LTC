package parkhomov.andrew.lensthicknesscalculator.utils.navigation

import androidx.fragment.app.Fragment
import parkhomov.andrew.diameter.view.Diameter
import parkhomov.andrew.glossary.view.Glossary
import parkhomov.andrew.thickness.view.Thickness
import parkhomov.andrew.transposition.view.Transposition
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class GetBottomTabFragment(private val tabId: String) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return when (tabId) {
                Diameter.TAG -> Diameter.instance
                Transposition.TAG -> Transposition.instance
                Glossary.TAG -> Glossary.instance
                else -> throw RuntimeException("No screen")
            }
        }
    }

}
