package parkhomov.andrew.ltc.domain

import kotlinx.coroutines.flow.StateFlow
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.LensData

interface CompareLensStorage {
    val compareList: StateFlow<Set<CalculatedData>>
    fun addItem(itemToAdd: CalculatedData)
    fun removeItem(itemToRemove: CalculatedData)
    fun clearCompareList()
    fun isInStorage(lensData: CalculatedData?): Boolean
}
