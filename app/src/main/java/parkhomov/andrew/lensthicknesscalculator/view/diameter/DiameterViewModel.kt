package parkhomov.andrew.lensthicknesscalculator.view.diameter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import parkhomov.andrew.lensthicknesscalculator.interactor.Interactor

class DiameterViewModel(
    private val interactor: Interactor
) : ViewModel() {

    val onFabClicked: Flow<Unit> = interactor.onFabClicked

    fun setMainFabIcon(imageId: Int) {
        interactor.setMainFabIcon(imageId)
    }

    fun onGlossaryItemClicked(imageId: Int)= interactor.setGlossaryItemClicked(imageId)
}


