package parkhomov.andrew.ltc.domain

import kotlinx.coroutines.flow.StateFlow
import parkhomov.andrew.ltc.data.LensData

interface CompareLensStorage {
    val compareList: StateFlow<Set<LensData>>
    fun addItem(itemToAdd: LensData)
    fun removeItem(itemToRemove: LensData)
    fun clearCompareList()
    fun isInStorage(lensData: LensData?): Boolean
}
