@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.compare

import kotlinx.coroutines.flow.collectLatest
import parkhomov.andrew.ltc.base.AppViewModel
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.domain.CompareLensStorage
import parkhomov.andrew.ltc.ui.compare.data.CompareLensScreenUiEvent
import parkhomov.andrew.ltc.ui.compare.data.CompareLensScreenUiState
import kotlin.time.ExperimentalTime

class CompareLensViewModel(
    private val compareLensStorage: CompareLensStorage,
) : AppViewModel<CompareLensScreenUiState, CompareLensScreenUiEvent>() {
    override val initialState: CompareLensScreenUiState
        get() = CompareLensScreenUiState()

    init {
        launch {
            compareLensStorage.compareList
                .collectLatest { compareList: Set<LensData> ->
                    updateState { copy(compareList = compareList.sortedBy { it.refractiveIndex.value }) }
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
        compareLensStorage.clearCompareList()
        uiEvent(CompareLensScreenUiEvent.CloseScreen)
    }

}
