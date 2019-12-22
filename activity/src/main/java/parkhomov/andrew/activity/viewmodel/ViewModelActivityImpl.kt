package parkhomov.andrew.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.activity.R
import parkhomov.andrew.base.interactor.Interactor
import ru.terrakok.cicerone.Router

class ViewModelActivityImpl(
        private val state: MediatorLiveData<State>,
        private val interactor: Interactor,
        private val router: Router
) : ViewModelActivity() {

    override fun getState(): LiveData<State> = state

    override fun showCompareListScreen() {
        router.navigateTo(parkhomov.andrew.comparelist.navigation.Screens.ScreenCompareList)
    }

    override fun onShareResultClicked() {
        if (interactor.calculatedData != null) {
            state.value = State.CreateStringForSharing(interactor.calculatedData!!)
        } else {
            state.value = State.ShowSnackbar(R.string.share_result_is_empty)
        }
    }
}
