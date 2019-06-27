package parkhomov.andrew.thickness.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.thickness.usecase.UseCaseThickness

abstract class ViewModelThickness : ViewModel() {

    sealed class State {
        data class HighlightSpherePower(val isShowError: Boolean) : State()
        data class HighlightDiameter(val isShowError: Boolean) : State()
        data class HighlightCenterThickness(val isShowError: Boolean) : State()
        data class SetCurrentBaseCurve(val curveValue: String) : State()
        class ShowResultDialog(val calculatedData: CalculatedData) : State()
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
