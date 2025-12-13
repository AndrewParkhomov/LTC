package parkhomov.andrew.ltc.ui.main.data

import parkhomov.andrew.ltc.data.LensData


sealed interface MainScreenUiEvent {
    data object OnCompareClick : MainScreenUiEvent
    data object HideResultDialog : MainScreenUiEvent
    data class OnCalculateThickness(val lensData: LensData) : MainScreenUiEvent
}
