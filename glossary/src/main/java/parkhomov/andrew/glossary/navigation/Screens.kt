package parkhomov.andrew.glossary.navigation

import androidx.fragment.app.Fragment
import parkhomov.andrew.glossary.view.Glossary
import parkhomov.andrew.thickness.view.Thickness
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object ScreenGlossary : SupportAppScreen() {
        override fun getFragment(): Fragment = Glossary.instance
    }

}
