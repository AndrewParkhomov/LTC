@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.compare.data

import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.LensData
import kotlin.time.ExperimentalTime

data class CompareLensScreenUiState(
    val compareList: List<CalculatedData> = listOf(),
    val closeScreen: Boolean = false,
) {
    companion object Companion {
        fun mock(): CompareLensScreenUiState {
            return CompareLensScreenUiState()
        }
    }
}
