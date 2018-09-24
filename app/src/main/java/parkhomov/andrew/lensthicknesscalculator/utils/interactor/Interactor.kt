package parkhomov.andrew.lensthicknesscalculator.utils.interactor

import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.utils.appLanguage
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.PreferencesHelper
import javax.inject.Inject

class Interactor
@Inject constructor(
        private val preferencesHelper: PreferencesHelper
) {
    var calculatedData: CalculatedData? = null

    fun getAppLanguage(): String = preferencesHelper.getStringValue(appLanguage, "en")
    fun saveNewAppLanguage(language: String) = preferencesHelper.putStringValue(appLanguage, language)

}