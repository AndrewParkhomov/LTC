package parkhomov.andrew.activity.usecase

import parkhomov.andrew.activity.R
import parkhomov.andrew.base.interactor.Interactor
import parkhomov.andrew.base.usecase.BaseUseCase
import parkhomov.andrew.base.utils.diameter
import parkhomov.andrew.base.utils.thickness
import parkhomov.andrew.base.utils.transposition

class UseCaseActivityImpl(
        private val interactor: Interactor
) : BaseUseCase<UseCaseActivity.Result>(),
        UseCaseActivity {

    override fun selectTab(position: Int) {
        val screen = when (position) {
            thickness -> parkhomov.andrew.thickness.navigation.Screens.ScreenThickness.fragment
            diameter -> parkhomov.andrew.diameter.navigation.Screens.ScreenDiameter.fragment
            transposition -> parkhomov.andrew.transposition.navigation.Screens.ScreenTransposition.fragment
            else -> parkhomov.andrew.glossary.navigation.Screens.ScreenGlossary.fragment
        }
        liveData.value = UseCaseActivity.Result.OpenNewTab(screen)
    }

    override fun onShareResultClicked() {
        if (interactor.calculatedData != null) {
            liveData.value = UseCaseActivity.Result.CreateStringForSharing(interactor.calculatedData!!)
        } else {
            liveData.value = UseCaseActivity.Result.ShowSnackbar(R.string.share_result_is_empty)
        }
    }
}
