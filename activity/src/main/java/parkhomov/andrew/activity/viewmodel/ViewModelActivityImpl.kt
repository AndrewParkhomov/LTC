package parkhomov.andrew.activity.viewmodel

import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.activity.R
import parkhomov.andrew.base.interactor.Interactor

class ViewModelActivityImpl(
        private val interactor: Interactor
) : ViewModelActivity() {

    override val state: MutableLiveData<State> = MutableLiveData()

    override fun clearEvents() {
        state.value = null
    }

    override fun onShareResultClicked() {
        if (interactor.calculatedData != null) {
            state.value = State.CreateStringForSharing(interactor.calculatedData!!)
        } else {
            state.value = State.ShowSnackbar(R.string.share_result_is_empty)
        }
    }
}
