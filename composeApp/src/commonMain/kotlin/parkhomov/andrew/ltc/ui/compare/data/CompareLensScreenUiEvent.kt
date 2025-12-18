package parkhomov.andrew.ltc.ui.compare.data


sealed interface CompareLensScreenUiEvent {
    data object ClearList : CompareLensScreenUiEvent
    data object CloseScreen : CompareLensScreenUiEvent
    data object CloseConsumed : CompareLensScreenUiEvent
}
