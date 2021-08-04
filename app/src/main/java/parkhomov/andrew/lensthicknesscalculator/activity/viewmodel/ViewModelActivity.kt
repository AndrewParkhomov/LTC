package parkhomov.andrew.lensthicknesscalculator.activity.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

abstract class ViewModelActivity : ViewModel() {

    abstract val state: LiveData<State>
    abstract fun clearEvents()

    sealed class State {
        class CreateStringForSharing(val data: CalculatedData?) : State()
        data class ShowSnackbar(@StringRes val id: Int) : State()
    }

    abstract fun onShareResultClicked()
}
