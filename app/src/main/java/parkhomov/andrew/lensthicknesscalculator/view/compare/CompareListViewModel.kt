package parkhomov.andrew.lensthicknesscalculator.view.compare

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor

class CompareListViewModel(
    private val interactor: Interactor
) : ViewModel() {

    val onClearClicked: Flow<Unit> = interactor.clear
        .onEach { interactor.compareList.clear() }

    val getCompareList: Flow<MutableSet<CalculatedData>> = flow {
        emit(interactor.compareList)
    }

}


