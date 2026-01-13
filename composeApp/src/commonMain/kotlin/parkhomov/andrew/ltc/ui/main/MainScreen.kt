package parkhomov.andrew.ltc.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import parkhomov.andrew.ltc.data.CalculatedData
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

    state.calculatedData?.let { data: CalculatedData ->
        ResultDialog(
            data = data,
            isLensInCompareList = state.isLensInCompareList,
            uiEvent = viewModel::uiEvent
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
