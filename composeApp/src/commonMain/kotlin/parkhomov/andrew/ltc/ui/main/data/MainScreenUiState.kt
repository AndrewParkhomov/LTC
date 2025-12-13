package parkhomov.andrew.ltc.ui.main.data

import parkhomov.andrew.ltc.data.CalculatedData

data class MainScreenUiState(
    val loadExistingGame: String? = null,
    val frontCurve: String? = null,
    val showCenterThicknessError: Boolean = false,
    val showResultDialog: CalculatedData? = null,
) {
    companion object Companion {
        fun mock(): MainScreenUiState {
            return MainScreenUiState(

            )
        }
    }
}
