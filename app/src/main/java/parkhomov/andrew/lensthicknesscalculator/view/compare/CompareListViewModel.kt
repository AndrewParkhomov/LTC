package parkhomov.andrew.lensthicknesscalculator.view.compare

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor

class CompareListViewModel(
    private val interactor: Interactor
) : ViewModel() {

    val onFabClicked: Flow<Unit> = interactor.onFabClicked

    val getCompareList: Flow<Set<CalculatedData>> = flow {
        emit(interactor.compareList)
    }

    fun setMainFabIcon(imageId: Int) {
        interactor.setMainFabIcon(imageId)
    }

    fun clearCompareList() {
        interactor.compareList.clear()
    }

}


