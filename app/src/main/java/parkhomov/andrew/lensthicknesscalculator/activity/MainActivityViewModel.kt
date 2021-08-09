package parkhomov.andrew.lensthicknesscalculator.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor

class MainActivityViewModel(
    private val interactor: Interactor
) : ViewModel() {

    private val _showMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    val showMessage: StateFlow<Int?> = _showMessage.asStateFlow()

    private val _shareResult: MutableStateFlow<CalculatedData?> = MutableStateFlow(null)
    val shareResult: StateFlow<CalculatedData?> = _shareResult.asStateFlow()

    fun onCalculateClicked() = interactor.onCalculateClicked()
    fun onClearClicked() = interactor.onClearClicked()

    fun onShareResultClicked() {
        val lastCalculatedLens = interactor.calculatedData
        if (lastCalculatedLens != null) {
            _shareResult.tryEmit(lastCalculatedLens)
        } else {
            _showMessage.tryEmit(R.string.share_result_is_empty)
        }
    }

}
