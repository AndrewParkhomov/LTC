package parkhomov.andrew.lensthicknesscalculator.interactor

import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

class Interactor {
    var calculatedData: CalculatedData? = null
    var compareList: MutableSet<CalculatedData> = mutableSetOf()
}