package parkhomov.andrew.thickness.viewmodel

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.thickness.usecase.UseCaseThickness

class ViewModelThicknessImpl(
        private val state: MediatorLiveData<State>,
        private val useCaseThickness: UseCaseThickness
) : ViewModelThickness() {

    init {
        state.addSource(useCaseThickness.getLiveData(), ::handleUseCaseResult)
    }

    override fun onCleared() {
        useCaseThickness.cleanUp()
    }

    override fun getState(): LiveData<State> = state

    override fun onCalculateBtnClicked(
            childFragmentManager: FragmentManager,
            lensIndex: Triple<Double, Double, String>,
            spherePowerString: String,
            cylinderPowerString: String,
            axisString: String,
            curveString: String,
            centerThicknessString: String,
            edgeThicknessString: String,
            diameterString: String
    ) {
        useCaseThickness.calculateThickness(
                childFragmentManager,
                lensIndex,
                spherePowerString,
                cylinderPowerString,
                axisString,
                curveString,
                centerThicknessString,
                edgeThicknessString,
                diameterString
        )
    }

    private fun handleUseCaseResult(result: UseCaseThickness.Result?) {
        when (result) {
            is UseCaseThickness.Result.HighlightSpherePower -> {
                state.value = State.HighlightSpherePower(result.isShowError)
            }
            is UseCaseThickness.Result.HighlightDiameter -> {
                state.value = State.HighlightDiameter(result.isShowError)
            }
            is UseCaseThickness.Result.HighlightCenterThickness -> {
                state.value = State.HighlightCenterThickness(result.isShowError)
            }
            is UseCaseThickness.Result.SetCurrentBaseCurve -> {
                state.value = State.SetCurrentBaseCurve(result.curveValue)
            }
        }
    }

}
