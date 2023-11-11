package parkhomov.andrew.lensthicknesscalculator.view.compare

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor

class CompareListViewModel(
    private val interactor: Interactor
) : ViewModel() {

    val getCompareList: Flow<MutableSet<CalculatedData>> = flow {
        emit(interactor.compareList)
    }

    fun onClearClicked(){
        interactor.compareList.clear()
    }

}


