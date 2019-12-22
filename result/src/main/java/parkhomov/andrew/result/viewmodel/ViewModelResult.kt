package parkhomov.andrew.result.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import parkhomov.andrew.base.data.result.CalculatedData

abstract class ViewModelResult : ViewModel() {

    abstract val state: LiveData<State>
    abstract fun clearEvents()

    sealed class State {
        data class AddToList(val isSuccess: Boolean) : State()
        data class RemoveFromList(val isSuccess: Boolean) : State()
        data class CheckState(val isInList: Boolean) : State()
    }

    abstract fun addToList(data: CalculatedData)
    abstract fun removeFromList(data: CalculatedData)
    abstract fun checkState(data: CalculatedData)

}
