package parkhomov.andrew.diameter.navigation

import androidx.fragment.app.Fragment
import parkhomov.andrew.diameter.view.Diameter
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object ScreenDiameter : SupportAppScreen() {
        override fun getFragment(): Fragment = Diameter.instance
    }

}
