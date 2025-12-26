package parkhomov.andrew.ltc.ui.compare

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import parkhomov.andrew.ltc.ui.compare.data.CompareLensScreenUiEvent
import parkhomov.andrew.ltc.ui.compare.data.CompareLensScreenUiState

@Preview
@Composable
fun CompareLensScreen(
    viewModel: CompareLensViewModel = koinViewModel(),
    closeScreen: () -> Unit = {}
) {
    val state: CompareLensScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.closeScreen) {
        if (state.closeScreen) {
            closeScreen()
            viewModel.uiEvent(CompareLensScreenUiEvent.CloseConsumed)
        }
    }

    CompareLensUi(
        uiData = state,
        uiEvent = viewModel::uiEvent
    )
}
