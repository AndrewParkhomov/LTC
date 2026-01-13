package parkhomov.andrew.ltc.ui.main.data

data class TopBarActions(
    val onCompareClick: () -> Unit,
    val onCompareLongClick: () -> Unit,
    val onTransposeClick: () -> Unit,
    val onSettingsClick: () -> Unit,
)
