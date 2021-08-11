package parkhomov.andrew.lensthicknesscalculator.view.result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor

class ResultViewModel(
    private val interactor: Interactor
) : ViewModel() {

    val getCompareList: Flow<MutableSet<CalculatedData>> = flow {
        emit(interactor.compareList)
    }

}


