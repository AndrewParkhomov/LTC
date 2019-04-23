package parkhomov.andrew.transposition.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ViewModeTransposition : ViewModel() {

    sealed class State {
        data class SetValue(val value: Double, val viewId: Int) : State()
        data class CalculatedTransposition(val sphere: Double, val cylinder: Double, val axis: Double) : State()
        object ClearEditText : State()
    }

    abstract fun getState(): LiveData<State>

    abstract fun convertToValue(value: String?, viewId: Int)

}
