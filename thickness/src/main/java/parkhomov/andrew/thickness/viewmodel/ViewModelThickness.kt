package parkhomov.andrew.thickness.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelThickness : ViewModel() {

    sealed class State {
        data class LanguageReceived(val radioButtonId: Int) : State()
    }

    abstract fun getState(): LiveData<State>

    abstract fun onCalculateBtnClicked(
            lensIndex: Triple<Double, Double, String>,
            spherePowerString: String,
            cylinderPowerString: String,
            axisString: String,
            curveString: String,
            centerThicknessString: String,
            edgeThicknessString: String,
            diameterString: String
    )
}
