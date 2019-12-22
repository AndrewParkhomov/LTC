package parkhomov.andrew.transposition.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelTransposition : ViewModel() {

    abstract val state: LiveData<State>
    abstract fun clearEvents()

    sealed class State {
        data class SetValue(val value: Double, val viewId: Int) : State()
        data class CalculatedTransposition(val sphere: Double, val cylinder: Double, val axis: Double) : State()
        object ClearEditText : State()
    }

    abstract fun convertToValue(value: String?, viewId: Int)

}
