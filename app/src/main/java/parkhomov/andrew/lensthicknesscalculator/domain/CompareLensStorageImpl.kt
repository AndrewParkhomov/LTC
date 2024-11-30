package parkhomov.andrew.lensthicknesscalculator.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

class CompareLensStorageImpl : CompareLensStorage {

    private val _compareList = MutableStateFlow<Set<CalculatedData>>(mutableSetOf())
    override val compareList: StateFlow<Set<CalculatedData>> = _compareList.asStateFlow()

//    override fun updateCompareList(){
//
//    }

    override fun addItem(itemToAdd: CalculatedData) {
        _compareList.update { it + itemToAdd }
    }

    override fun removeItem(itemToRemove: CalculatedData) {
        _compareList.update {
            it.mapNotNull { cachedItem: CalculatedData ->
                if (cachedItem == itemToRemove) {
                    null
                } else {
                    cachedItem
                }
            }.toSet()
        }
    }

    override fun clearCompareList() {
        _compareList.update { setOf() }
    }
}