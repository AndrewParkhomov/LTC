package parkhomov.andrew.thickness.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.thickness.domain.UseCaseThickness

class ViewModelThicknessImpl(
        private val state: MediatorLiveData<State>,
        private val useCaseLanguage: UseCaseThickness
) : ViewModelThickness() {

    init {
        state.addSource(useCaseLanguage.getLiveData(), ::onGetRadioButtonIdResult)
    }

    override fun onCleared() {
        useCaseLanguage.cleanUp()
    }

    override fun getState(): LiveData<State> = state

    override fun onCalculateBtnClicked(
            lensIndex: Triple<Double, Double, String>,
            spherePowerString: String,
            cylinderPowerString: String,
            axisString: String,
            curveString: String,
            centerThicknessString: String,
            edgeThicknessString: String,
            diameterString: String
    ){

    }

    private fun onGetRadioButtonIdResult(result: UseCaseThickness.Result?) {
        when (result) {
            is UseCaseThickness.Result.RadioButtonId -> {
                state.value = State.LanguageReceived(result.radioButtonId)
            }
        }
    }
}
