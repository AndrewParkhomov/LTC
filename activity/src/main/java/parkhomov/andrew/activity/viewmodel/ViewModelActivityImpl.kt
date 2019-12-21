package parkhomov.andrew.activity.viewmodel

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.activity.usecase.UseCaseActivity

class ViewModelActivityImpl(
        private val state: MediatorLiveData<State>,
        private val useCaseActivity: UseCaseActivity
) : ViewModelActivity() {

    init {
        state.addSource(useCaseActivity.getLiveData(), ::handleUseCaseResult)
    }

    override fun onCleared() {
        useCaseActivity.cleanUp()
    }

    override fun getState(): LiveData<State> = state

    private fun handleUseCaseResult(result: UseCaseActivity.Result?) {
        when (result) {
            is UseCaseActivity.Result.OpenNewTab -> {
                state.value = State.OpenNewTab(result.screen)
            }
            is UseCaseActivity.Result.CreateStringForSharing -> {
                state.value = State.CreateStringForSharing(result.data)
            }
            is UseCaseActivity.Result.ShowSnackbar -> {
                state.value = State.ShowSnackbar(result.id)
            }
        }
    }

    override fun onLanguageItemClicked(supportFragmentManager: FragmentManager) {
        useCaseActivity.onLanguageItemClicked(supportFragmentManager)
    }

    override fun showCompareListScreen() {
        useCaseActivity.showCompareListScreen()
    }

    override fun selectTab(position: Int) {
        useCaseActivity.selectTab(position)
    }

    override fun onShareResultClicked() {
        useCaseActivity.onShareResultClicked()
    }
}
