package parkhomov.andrew.diameter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelDiameter : ViewModel() {

    abstract fun getState(): LiveData<State>

    sealed class State {
        data class SetValue(val value: Double, val viewId: Int) : State()
        data class ShowDiameterResult(val targetValue: Double) : State()
    }



    abstract fun setSize(size: String?, viewId: Int)

}
