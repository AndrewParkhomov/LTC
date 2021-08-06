package parkhomov.andrew.lensthicknesscalculator.view.transposition

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor

class TranspositionViewModel(
    private val interactor: Interactor
) : ViewModel() {

    fun onGlossaryItemClicked(imageId: Int) = interactor.setGlossaryItemClicked(imageId)

    fun setMainFabIcon(imageId: Int) {
        interactor.setMainFabIcon(imageId)
    }

}


