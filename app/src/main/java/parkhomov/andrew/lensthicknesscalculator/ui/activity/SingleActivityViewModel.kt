package parkhomov.andrew.lensthicknesscalculator.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.mvvm.*
import parkhomov.andrew.lensthicknesscalculator.utils.*
import parkhomov.andrew.lensthicknesscalculator.utils.interactor.Interactor

class SingleActivityViewModel(
        private val interactor: Interactor
) : CoroutinesViewModel() {

    private val _events = MutableLiveData<ViewModelState>()
    val events: LiveData<ViewModelState>
        get() = _events

    fun initViews() {
        _events.value = OpenNewTab(interactor.selectedTabId)
        _events.value = MakeTabSelected(interactor.position)
    }

    fun selectTab(tabId: String, position: Int) {
        interactor.selectedTabId = tabId
        interactor.position = position
        _events.value = OpenNewTab(interactor.selectedTabId)
        _events.value = MakeTabSelected(interactor.position)
    }

    fun onShareResultClicked() {
        if (interactor.calculatedData != null) {
            _events.value = CreateStringForSharing(interactor.calculatedData!!)
        } else {
            _events.value = ShowSnackbar(R.string.share_result_is_empty)
        }
    }
}