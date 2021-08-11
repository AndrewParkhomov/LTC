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

    fun onCalculateClicked() = interactor.onCalculateClicked()
    fun onClearClicked() = interactor.onClearClicked()

}
