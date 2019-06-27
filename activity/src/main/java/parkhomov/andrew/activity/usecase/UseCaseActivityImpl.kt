package parkhomov.andrew.activity.usecase

import androidx.fragment.app.FragmentManager
import parkhomov.andrew.activity.R
import parkhomov.andrew.base.helper.NavigationI
import parkhomov.andrew.base.interactor.Interactor
import parkhomov.andrew.base.usecase.BaseUseCase

class UseCaseActivityImpl(
        private val interactor: Interactor,
        private val navigation: NavigationI
) : BaseUseCase<UseCaseActivity.Result>(),
        UseCaseActivity {

    override fun selectTab(position: Int) {
        liveData.value = UseCaseActivity.Result.OpenNewTab(navigation.getSelectedFragment(position))
    }

    override fun onLanguageItemClicked(supportFragmentManager: FragmentManager) {
        navigation.showLanguageDialog(supportFragmentManager)
    }

    override fun onShareResultClicked() {
        if (interactor.calculatedData != null) {
            liveData.value = UseCaseActivity.Result.CreateStringForSharing(interactor.calculatedData!!)
        } else {
            liveData.value = UseCaseActivity.Result.ShowSnackbar(R.string.share_result_is_empty)
        }
    }
}
