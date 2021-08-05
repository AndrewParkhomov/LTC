package parkhomov.andrew.lensthicknesscalculator.activity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.interactor.Interactor

class MainActivityViewModel(
    private val interactor: Interactor
) : ViewModel() {

    val imageFab: SharedFlow<Int> = interactor.fabIcon
    val showGlossary: SharedFlow<Int> = interactor.showGlossary

    private val _showMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    val showMessage: StateFlow<Int?> = _showMessage.asStateFlow()

    private val _shareResult: MutableStateFlow<CalculatedData?> = MutableStateFlow(null)
    val shareResult: StateFlow<CalculatedData?> = _shareResult.asStateFlow()

    fun onFabClicked() {
        interactor.onFabClicked()
    }

    fun onShareResultClicked() {
        val lastCalculatedLens = interactor.calculatedData
        if (lastCalculatedLens != null) {
            _shareResult.tryEmit(lastCalculatedLens)
        } else {
            _showMessage.tryEmit(R.string.share_result_is_empty)
        }
    }

}
