package parkhomov.andrew.lensthicknesscalculator.view.result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.domain.CompareLensStorageImpl

class ResultViewModel(
    private val compareLensStorage: CompareLensStorageImpl
) : ViewModel() {

    val compareList: StateFlow<Set<CalculatedData>> = compareLensStorage.compareList

    fun removeCompareItem(itemToRemove: CalculatedData) {
        compareLensStorage.removeItem(itemToRemove)
    }

    fun addCompareItem(itemToAdd: CalculatedData) {
        compareLensStorage.addItem(itemToAdd)
    }

}
