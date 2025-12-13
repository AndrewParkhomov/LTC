package parkhomov.andrew.ltc.view.result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import parkhomov.andrew.ltc.data.CalculatedDataOld
import parkhomov.andrew.ltc.domain.CompareLensStorageImpl

class ResultViewModel(
    private val compareLensStorage: CompareLensStorageImpl
) : ViewModel() {

    val compareList: StateFlow<Set<CalculatedDataOld>> = compareLensStorage.compareList

    fun removeCompareItem(itemToRemove: CalculatedDataOld) {
        compareLensStorage.removeItem(itemToRemove)
    }

    fun addCompareItem(itemToAdd: CalculatedDataOld) {
        compareLensStorage.addItem(itemToAdd)
    }

}
