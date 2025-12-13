package parkhomov.andrew.ltc.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiState
import parkhomov.andrew.ltc.ui.main.modal.ResultDialog

@Preview
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    onCompareClick: () -> Unit = {},
) {
    val state: MainScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()

    state.showResultDialog?.let { data ->
        ResultDialog(
            data = data,
            onDismiss = {
                viewModel.uiEvent(MainScreenUiEvent.HideResultDialog)
            },
            onShare = { /* TODO: Share */ },
            onAddToCompare = { /* TODO: Add to compare */ }
        )
    }

    MainScreenUi(
        uiData = state,
        uiEvent = viewModel::uiEvent
    )
}
