package parkhomov.andrew.lensthicknesscalculator.domain

import kotlinx.coroutines.flow.StateFlow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

interface CompareLensStorage {
    val compareList: StateFlow<Set<CalculatedData>>
    fun addItem(itemToAdd: CalculatedData)
    fun removeItem(itemToRemove: CalculatedData)
    fun clearCompareList()
}
