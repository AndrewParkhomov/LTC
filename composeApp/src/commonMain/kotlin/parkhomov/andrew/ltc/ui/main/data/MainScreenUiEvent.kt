package parkhomov.andrew.ltc.ui.main.data

import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.theme.ThemeMode


sealed interface MainScreenUiEvent {
    data object OnCompareClick : MainScreenUiEvent
    data object HideResultDialog : MainScreenUiEvent
    data class OnCalculateThickness(val lensData: LensData) : MainScreenUiEvent
    data object OnAddToCompareClicked : MainScreenUiEvent
    data object OnRemoveFromCompareListClicked : MainScreenUiEvent
    data object OnTranspositionIconClick : MainScreenUiEvent
    data class DoTransposition(val lensData: LensData) : MainScreenUiEvent
    data class UpdateAppLanguage(val language: String): MainScreenUiEvent
    data class UpdateAppTheme(val theme: ThemeMode): MainScreenUiEvent
}
