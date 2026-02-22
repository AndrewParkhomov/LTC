package parkhomov.andrew.ltc.ui.main.data

import parkhomov.andrew.ltc.data.DiameterData
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndexUiModel
import parkhomov.andrew.ltc.theme.ThemeMode

sealed interface MainScreenUiEvent {
    data object OnCompareClick : MainScreenUiEvent
    data object HideResultDialog : MainScreenUiEvent
    data class OnCalculateThickness(val lensData: LensData) : MainScreenUiEvent
    data object OnAddToCompareClicked : MainScreenUiEvent
    data object OnRemoveFromCompareListClicked : MainScreenUiEvent
    data object OnTranspositionIconClick : MainScreenUiEvent
    data class DoTransposition(val lensData: LensData) : MainScreenUiEvent
    data class UpdateAppLanguage(val language: String) : MainScreenUiEvent
    data class UpdateAppTheme(val theme: ThemeMode) : MainScreenUiEvent
    data object ShowSettingsDialog : MainScreenUiEvent
    data object HideSettingsDialog : MainScreenUiEvent
    data class SelectRefractiveIndex(val index: RefractiveIndexUiModel) : MainScreenUiEvent
    data object OnAddCustomIndexClick : MainScreenUiEvent
    data object HideAddCustomIndexDialog : MainScreenUiEvent
    data class SaveCustomIndex(val label: String, val value: Double) : MainScreenUiEvent
    data class OnDeleteCustomIndexClick(val index: RefractiveIndexUiModel) : MainScreenUiEvent
    data object HideDeleteConfirmDialog : MainScreenUiEvent
    data object ConfirmDeleteIndex : MainScreenUiEvent
    data object HideIosPromoDialog : MainScreenUiEvent
    data object ClearLensData : MainScreenUiEvent
    data class OnDiameterCalculated(val data: DiameterData) : MainScreenUiEvent
}
