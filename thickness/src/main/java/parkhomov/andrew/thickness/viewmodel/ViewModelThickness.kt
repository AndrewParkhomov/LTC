package parkhomov.andrew.thickness.viewmodel

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelThickness : ViewModel() {

    sealed class State {
        data class HighlightSpherePower(val isShowError: Boolean) : State()
        data class HighlightDiameter(val isShowError: Boolean) : State()
        data class HighlightCenterThickness(val isShowError: Boolean) : State()
        data class SetCurrentBaseCurve(val curveValue: String) : State()
    }

    abstract fun getState(): LiveData<State>

    abstract fun onCalculateBtnClicked(
            childFragmentManager: FragmentManager,
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
