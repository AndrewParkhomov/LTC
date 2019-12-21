package parkhomov.andrew.comparelist.navigation

import androidx.fragment.app.Fragment
import parkhomov.andrew.comparelist.view.CompareList
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object ScreenCompareList : SupportAppScreen() {
        override fun getFragment(): Fragment = CompareList.instance
    }

}
