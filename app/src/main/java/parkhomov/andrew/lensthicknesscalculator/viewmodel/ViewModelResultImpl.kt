package parkhomov.andrew.lensthicknesscalculator.viewmodel

import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.interactor.Interactor

class ViewModelResultImpl(
        private val interactor: Interactor
) : ViewModelResult() {

    override val state: MutableLiveData<State> = MutableLiveData()

    override fun clearEvents() {
        state.value = null
    }

    override fun addToList(data: CalculatedData) {
        state.value = State.AddToList(interactor.compareList.add(data))
    }

    override fun removeFromList(data: CalculatedData) {
        state.value = State.RemoveFromList(interactor.compareList.remove(data))
    }

    override fun checkState(data: CalculatedData) {
        state.value = State.CheckState(interactor.compareList.contains(data))
    }
}


