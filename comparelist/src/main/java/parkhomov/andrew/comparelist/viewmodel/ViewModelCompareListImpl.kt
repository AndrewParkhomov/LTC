package parkhomov.andrew.comparelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.base.interactor.Interactor

class ViewModelCompareListImpl(
        private val state: MediatorLiveData<State>,
        private val interactor: Interactor
) : ViewModelCompareList() {

    override val getState: LiveData<State> = state

    override fun onCleared() {

    }

    override fun getListForCompare() {
        state.value = State.ListToCompare(interactor.compareList)
    }

}


