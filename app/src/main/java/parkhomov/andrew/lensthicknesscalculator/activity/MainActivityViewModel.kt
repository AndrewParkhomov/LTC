package parkhomov.andrew.lensthicknesscalculator.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor

class MainActivityViewModel(
    private val interactor: Interactor
) : ViewModel() {

    fun onCalculateClicked() = viewModelScope.launch { interactor.onCalculateClicked() }
    fun onClearClicked() = viewModelScope.launch { interactor.onClearClicked() }

}
