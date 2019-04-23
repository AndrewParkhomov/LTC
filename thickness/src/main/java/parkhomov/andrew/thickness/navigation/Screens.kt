package parkhomov.andrew.thickness.navigation

import androidx.fragment.app.Fragment
import parkhomov.andrew.thickness.view.Thickness
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object ScreenThickness : SupportAppScreen() {
        override fun getFragment(): Fragment = Thickness.instance
    }

}
