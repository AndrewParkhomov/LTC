package parkhomov.andrew.ltc.ui.main.data

import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.LensData

data class MainScreenUiState(
    val autocalculatedFrontCurve: String? = null,
    val showCenterThicknessError: Boolean = false,
    val lensData: LensData? = null,
    val showResultDialog: CalculatedData? = null
) {
    companion object Companion {
        fun mock(): MainScreenUiState {
            return MainScreenUiState(

            )
        }
    }
}
