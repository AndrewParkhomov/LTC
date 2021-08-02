package parkhomov.andrew.lensthicknesscalculator.interactor

import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.helper.PreferencesHelper

class Interactor(
        private val preferencesHelper: PreferencesHelper
) {
    var calculatedData: CalculatedData? = null
    var compareList: MutableSet<CalculatedData> = mutableSetOf()
}