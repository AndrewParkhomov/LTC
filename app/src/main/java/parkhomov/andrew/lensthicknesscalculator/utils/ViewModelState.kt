package parkhomov.andrew.lensthicknesscalculator.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import parkhomov.andrew.base.data.result.CalculatedData

open class ViewModelState

// single activity
data class ShowSnackbar(@StringRes val id: Int) : ViewModelState()
data class CreateStringForSharing(val data: CalculatedData?) : ViewModelState()
data class MakeTabSelected(val position: Int) : ViewModelState()
data class OpenNewTab(val screen: Fragment?) : ViewModelState()