package parkhomov.andrew.ltc.view.compare

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import parkhomov.andrew.ltc.data.CalculatedDataOld
import parkhomov.andrew.ltc.domain.CompareLensStorageImpl

class CompareListViewModel(
    private val compareLensStorage: CompareLensStorageImpl
) : ViewModel() {

    val compareList: StateFlow<Set<CalculatedDataOld>> = compareLensStorage.compareList

    fun onClearClicked() = compareLensStorage.clearCompareList()
}
