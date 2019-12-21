package parkhomov.andrew.base.interactor

import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.utils.thickness
import javax.inject.Inject

class Interactor
@Inject constructor(
        private val preferencesHelper: PreferencesHelper
) {
    var calculatedData: CalculatedData? = null
    var compareList: MutableSet<CalculatedData> = mutableSetOf()

}