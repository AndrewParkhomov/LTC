package parkhomov.andrew.transposition.navigation

import androidx.fragment.app.Fragment
import parkhomov.andrew.transposition.view.Transposition
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object ScreenTransposition : SupportAppScreen() {
        override fun getFragment(): Fragment = Transposition.instance
    }

}
