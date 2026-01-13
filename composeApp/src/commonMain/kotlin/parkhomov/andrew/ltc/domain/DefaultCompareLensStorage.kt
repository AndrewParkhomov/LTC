package parkhomov.andrew.ltc.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import parkhomov.andrew.ltc.data.CalculatedData

class DefaultCompareLensStorage : CompareLensStorage {

    private val _compareList = MutableStateFlow<Set<CalculatedData>>(mutableSetOf())
    override val compareList: StateFlow<Set<CalculatedData>> = _compareList.asStateFlow()

    override fun addItem(itemToAdd: CalculatedData) {
        _compareList.update { it + itemToAdd }
    }

    override fun removeItem(itemToRemove: CalculatedData) {
        _compareList.update { it - itemToRemove }
    }

    override fun clearCompareList() {
        _compareList.update { setOf() }
    }

    override fun isInStorage(calculatedData: CalculatedData?): Boolean {
        calculatedData ?: return false
        return compareList.value.contains(calculatedData)
    }
}