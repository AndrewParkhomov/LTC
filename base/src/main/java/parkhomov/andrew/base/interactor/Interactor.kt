package parkhomov.andrew.base.interactor

import androidx.fragment.app.Fragment
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.utils.appLanguage
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

class Interactor
@Inject constructor(
        private val preferencesHelper: PreferencesHelper
) {
    var calculatedData: CalculatedData? = null
    // when recreate activity after change language open required tab
    var selectedTabId: Fragment? = null
    var position = 0

    fun getAppLanguage(): String = preferencesHelper.getStringValue(appLanguage, "en")

    fun saveNewAppLanguage(language: String) = preferencesHelper.putStringValue(appLanguage, language)

}