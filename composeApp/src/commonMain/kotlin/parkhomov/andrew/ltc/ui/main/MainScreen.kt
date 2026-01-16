package parkhomov.andrew.ltc.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.lyricist.LocalStrings
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.provider.ShareManager
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiState
import parkhomov.andrew.ltc.ui.main.modal.APP_STORE_LINK
import parkhomov.andrew.ltc.ui.main.modal.IosPromoDialog
import parkhomov.andrew.ltc.ui.main.modal.ResultDialog

@Preview
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    onCompareClick: () -> Unit = {},
) {
    val state: MainScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val strings = LocalStrings.current

    state.calculatedData?.let { data: CalculatedData ->
        ResultDialog(
            data = data,
            isLensInCompareList = state.isLensInCompareList,
            uiEvent = viewModel::uiEvent
        )
    }

    if (state.showIosPromoDialog) {
        IosPromoDialog(
            onShareClick = {
                scope.launch {
                    ShareManager().shareText(
                        text = APP_STORE_LINK,
                        title = strings.appNameFull
                    )
                }
            },
            onDismiss = {
                viewModel.uiEvent(MainScreenUiEvent.HideIosPromoDialog)
            }
        )
    }

    MainScreenUi(
        uiData = state,
        uiEvent = { uiEvent: MainScreenUiEvent ->
            when (uiEvent) {
                is MainScreenUiEvent.OnCompareClick -> onCompareClick()
                else -> viewModel.uiEvent(uiEvent)
            }
        }
    )
}
