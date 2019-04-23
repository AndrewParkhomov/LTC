package parkhomov.andrew.transposition.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.transposition.domain.UseCaseTransposition

class ViewModelTranspositionImpl(
        private val state: MediatorLiveData<State>,
        private val useCaseTransposition: UseCaseTransposition
) : ViewModeTransposition() {

    init {
        state.addSource(useCaseTransposition.getLiveData(), ::handleUseCaseResult)
    }

    override fun onCleared() {
        useCaseTransposition.cleanUp()
    }

    override fun getState(): LiveData<State> = state

    override fun convertToValue(value: String?, viewId: Int) {
        useCaseTransposition.convertToValue(value, viewId)
    }

    private fun handleUseCaseResult(result: UseCaseTransposition.Result?) {
        when (result) {
            is UseCaseTransposition.Result.SetValue -> {
                state.value = State.SetValue(result.value, result.viewId)
            }
            is UseCaseTransposition.Result.CalculatedTransposition -> {
                state.value = State.CalculatedTransposition(result.sphere, result.cylinder, result.axis)
            }
            is UseCaseTransposition.Result.ClearEditText -> {
                state.value = State.ClearEditText
            }
        }
    }
}


