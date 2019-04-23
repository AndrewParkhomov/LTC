package parkhomov.andrew.lensthicknesscalculator.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.base.interactor.Interactor
import parkhomov.andrew.diameter.view.Diameter
import parkhomov.andrew.glossary.view.Glossary
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.mvvm.CoroutinesViewModel
import parkhomov.andrew.lensthicknesscalculator.utils.*
import parkhomov.andrew.lensthicknesscalculator.utils.navigation.Screens
import parkhomov.andrew.transposition.view.Transposition

class SingleActivityViewModel(
        private val interactor: Interactor
) : CoroutinesViewModel() {

    private val _events = MutableLiveData<ViewModelState>()
    val events: LiveData<ViewModelState>
        get() = _events

    fun initViews() {
        interactor.selectedTabId = parkhomov.andrew.thickness.navigation.Screens.ScreenThickness.fragment
        _events.value = OpenNewTab(interactor.selectedTabId)
        _events.value = MakeTabSelected(interactor.position)
    }

    fun selectTab(tabId: String, position: Int) {
        val screen = when (tabId) {
            Diameter.TAG,
            Transposition.TAG,
            Glossary.TAG -> Screens.GetBottomTabFragment(tabId).fragment
            else -> parkhomov.andrew.thickness.navigation.Screens.ScreenThickness.fragment
        }
        interactor.selectedTabId = screen
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