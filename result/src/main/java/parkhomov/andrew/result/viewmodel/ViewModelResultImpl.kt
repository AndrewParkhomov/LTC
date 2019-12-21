package parkhomov.andrew.result.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.interactor.Interactor

class ViewModelResultImpl(
        private val state: MediatorLiveData<State>,
        private val interactor: Interactor
) : ViewModelResult() {

    override fun getState(): LiveData<State> = state

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


