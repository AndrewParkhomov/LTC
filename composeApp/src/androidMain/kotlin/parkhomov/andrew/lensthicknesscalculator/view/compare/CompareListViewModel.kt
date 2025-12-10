package parkhomov.andrew.lensthicknesscalculator.view.compare

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.domain.CompareLensStorageImpl

class CompareListViewModel(
    private val compareLensStorage: CompareLensStorageImpl
) : ViewModel() {

    val compareList: StateFlow<Set<CalculatedData>> = compareLensStorage.compareList

    fun onClearClicked() = compareLensStorage.clearCompareList()
}
