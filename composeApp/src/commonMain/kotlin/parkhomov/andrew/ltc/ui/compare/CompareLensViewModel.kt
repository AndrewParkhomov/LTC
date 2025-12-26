@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.compare

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import parkhomov.andrew.ltc.base.AppViewModel
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.domain.CompareLensStorage
import parkhomov.andrew.ltc.ui.compare.data.CompareLensScreenUiEvent
import parkhomov.andrew.ltc.ui.compare.data.CompareLensScreenUiState
import kotlin.time.ExperimentalTime

@Stable
class CompareLensViewModel(
    private val compareLensStorage: CompareLensStorage,
) : AppViewModel<CompareLensScreenUiState, CompareLensScreenUiEvent>() {
    override val initialState: CompareLensScreenUiState
        get() = CompareLensScreenUiState()

    init {
        launch {
            compareLensStorage.compareList
                .collectLatest { compareList: Set<CalculatedData> ->
                    updateState {
                        copy(
                            compareList = compareList
                            .sortedBy { it.refractionIndex.value }
                            .toImmutableList()
                        )
                    }
                }
        }
    }

    override fun uiEvent(event: CompareLensScreenUiEvent) {
        when (event) {
            is CompareLensScreenUiEvent.ClearList -> handleClearListButtonClick()
            is CompareLensScreenUiEvent.CloseScreen -> updateState { copy(closeScreen = true) }
            is CompareLensScreenUiEvent.CloseConsumed -> updateState { copy(closeScreen = false) }
        }
    }

    private fun handleClearListButtonClick() {
        uiEvent(CompareLensScreenUiEvent.CloseScreen)
        compareLensStorage.clearCompareList()
    }

}
