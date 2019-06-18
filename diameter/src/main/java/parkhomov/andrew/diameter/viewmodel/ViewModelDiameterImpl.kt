package parkhomov.andrew.diameter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.diameter.domain.UseCaseDiameter

class ViewModelDiameterImpl(
        private val state: MediatorLiveData<State>,
        private val useCaseDiameter: UseCaseDiameter
) : ViewModeDiameter() {

    init {
        state.addSource(useCaseDiameter.getLiveData(), ::handleUseCaseResult)
    }

    override fun onCleared() {
        useCaseDiameter.cleanUp()
    }

    override fun getState(): LiveData<State> = state

    override fun setSize(size: String?, viewId: Int) {
        useCaseDiameter.setSize(size, viewId)
    }

    private fun handleUseCaseResult(result: UseCaseDiameter.Result?) {
        when (result) {
            is UseCaseDiameter.Result.SetValue -> {
                state.value = State.SetValue(result.value, result.viewId)
            }
            is UseCaseDiameter.Result.CalculatedDiameter -> {
                state.value = State.ShowDiameterResult(result.targetValue)
            }
        }
    }
}


