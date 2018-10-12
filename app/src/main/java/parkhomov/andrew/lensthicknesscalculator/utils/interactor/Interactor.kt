package parkhomov.andrew.lensthicknesscalculator.utils.interactor

import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.Thickness
import parkhomov.andrew.lensthicknesscalculator.utils.appLanguage
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.PreferencesHelper
import javax.inject.Inject

class Interactor
@Inject constructor(
        private val preferencesHelper: PreferencesHelper
) {
    var calculatedData: CalculatedData? = null
    // when recreate activity after change language open required tab
    var selectedTabId = Thickness.TAG
    var position = 0

    fun getAppLanguage(): String = preferencesHelper.getStringValue(appLanguage, "en")
    fun saveNewAppLanguage(language: String) = preferencesHelper.putStringValue(appLanguage, language)

}