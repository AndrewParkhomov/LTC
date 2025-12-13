package parkhomov.andrew.ltc.domain

import kotlinx.coroutines.flow.StateFlow
import parkhomov.andrew.ltc.data.CalculatedDataOld

interface CompareLensStorage {
    val compareList: StateFlow<Set<CalculatedDataOld>>
    fun addItem(itemToAdd: CalculatedDataOld)
    fun removeItem(itemToRemove: CalculatedDataOld)
    fun clearCompareList()
}
