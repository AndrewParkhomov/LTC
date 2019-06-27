package parkhomov.andrew.activity.viewmodel

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import parkhomov.andrew.base.data.result.CalculatedData

abstract class ViewModelActivity : ViewModel() {

    sealed class State {
        class CreateStringForSharing(val data: CalculatedData?) : State()
        data class ShowSnackbar(@StringRes val id: Int) : State()
        class OpenNewTab(val screen: Fragment?) : State()
    }

    abstract fun getState(): LiveData<State>

    abstract fun selectTab(position: Int)

    abstract fun onShareResultClicked()
}
