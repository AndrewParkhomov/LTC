package parkhomov.andrew.activity.usecase

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.usecase.UseCase


interface UseCaseActivity : UseCase<UseCaseActivity.Result> {

    sealed class Result {
        class CreateStringForSharing(val data: CalculatedData?) : Result()
        data class ShowSnackbar(@StringRes val id: Int) : Result()
        class OpenNewTab(val screen: Fragment?) : Result()
    }

    fun selectTab(position: Int)

    fun onShareResultClicked()

    fun onLanguageItemClicked(supportFragmentManager: FragmentManager)
    fun showCompareListScreen()
}
