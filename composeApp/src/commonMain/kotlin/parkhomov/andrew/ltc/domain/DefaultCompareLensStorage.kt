package parkhomov.andrew.ltc.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import parkhomov.andrew.ltc.data.LensData

class DefaultCompareLensStorage : CompareLensStorage {

    private val _compareList = MutableStateFlow<Set<LensData>>(mutableSetOf())
    override val compareList: StateFlow<Set<LensData>> = _compareList.asStateFlow()

    override fun addItem(itemToAdd: LensData) {
        _compareList.update { it + itemToAdd }
    }

    override fun removeItem(itemToRemove: LensData) {
        _compareList.update { it - itemToRemove }
    }

    override fun clearCompareList() {
        _compareList.update { setOf() }
    }

    override fun isInStorage(lensData: LensData?): Boolean {
        lensData ?: return false
        return compareList.value.contains(lensData)
    }
}