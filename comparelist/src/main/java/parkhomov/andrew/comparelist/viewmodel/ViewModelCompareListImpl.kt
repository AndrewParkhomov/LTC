package parkhomov.andrew.comparelist.viewmodel

import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.base.interactor.Interactor

class ViewModelCompareListImpl(
        private val interactor: Interactor
) : ViewModelCompareList() {

    override val state: MutableLiveData<State> = MutableLiveData()

    override fun clearEvents() {
        state.value = null
    }

    override fun clearCompareList() {
        interactor.compareList.clear()
    }

    override fun getListForCompare() {
        state.value = State.ListToCompare(interactor.compareList)
    }

}


