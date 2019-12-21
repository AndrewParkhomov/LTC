package parkhomov.andrew.comparelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import parkhomov.andrew.base.data.result.CalculatedData

abstract class ViewModelCompareList : ViewModel() {

    abstract val getState: LiveData<State>

    sealed class State {
        data class ListToCompare(val compareList: MutableSet<CalculatedData>) : State()
    }

    abstract fun getListForCompare()

}
