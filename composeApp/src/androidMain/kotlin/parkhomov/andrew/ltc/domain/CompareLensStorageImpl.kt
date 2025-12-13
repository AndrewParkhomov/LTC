package parkhomov.andrew.ltc.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import parkhomov.andrew.ltc.data.CalculatedDataOld

class CompareLensStorageImpl : CompareLensStorage {

    private val _compareList = MutableStateFlow<Set<CalculatedDataOld>>(mutableSetOf())
    override val compareList: StateFlow<Set<CalculatedDataOld>> = _compareList.asStateFlow()

    override fun addItem(itemToAdd: CalculatedDataOld) {
        _compareList.update { it + itemToAdd }
    }

    override fun removeItem(itemToRemove: CalculatedDataOld) {
        _compareList.update {
            it.mapNotNull { cachedItem: CalculatedDataOld ->
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