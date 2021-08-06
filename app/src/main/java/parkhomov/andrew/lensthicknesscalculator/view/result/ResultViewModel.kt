package parkhomov.andrew.lensthicknesscalculator.view.result

import androidx.lifecycle.ViewModel
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor

class ResultViewModel(
    private val interactor: Interactor
) : ViewModel() {

    fun addToList(data: CalculatedData) = interactor.compareList.add(data)
    fun removeFromList(data: CalculatedData) = interactor.compareList.remove(data)
    fun checkIsContains(data: CalculatedData) = interactor.compareList.contains(data)
}


