package parkhomov.andrew.lensthicknesscalculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

abstract class ViewModelCompareList : ViewModel() {

    abstract val state: LiveData<State>
    abstract fun clearEvents()

    sealed class State {
        data class ListToCompare(val compareList: MutableSet<CalculatedData>) : State()
    }

    abstract fun getListForCompare()
    abstract fun clearCompareList()
    abstract fun setMainFabIcon(imageId: Int)

    abstract val onFabClicked: Flow<Unit>
}
