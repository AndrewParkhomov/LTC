package parkhomov.andrew.activity.viewmodel

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.thickness.viewmodel.ViewModelThickness

abstract class ViewModelActivity : ViewModel() {

    abstract val state: LiveData<State>
    abstract fun clearEvents()

    sealed class State {
        class CreateStringForSharing(val data: CalculatedData?) : State()
        data class ShowSnackbar(@StringRes val id: Int) : State()
    }

    abstract fun onShareResultClicked()
}
