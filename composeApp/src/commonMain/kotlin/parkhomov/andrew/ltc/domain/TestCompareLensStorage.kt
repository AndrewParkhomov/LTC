package parkhomov.andrew.ltc.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import parkhomov.andrew.ltc.data.CalculatedData

class TestCompareLensStorage : CompareLensStorage {

    private val _compareList = MutableStateFlow<Set<CalculatedData>>(emptySet())
    override val compareList: StateFlow<Set<CalculatedData>> = _compareList.asStateFlow()

    override fun addItem(itemToAdd: CalculatedData) {
        _compareList.value += itemToAdd
    }

    override fun removeItem(itemToRemove: CalculatedData) {
        _compareList.value -= itemToRemove
    }

    override fun clearCompareList() {
        _compareList.value = emptySet()
    }

    override fun isInStorage(calculatedData: CalculatedData?): Boolean {
        return calculatedData != null && compareList.value.contains(calculatedData)
    }

}