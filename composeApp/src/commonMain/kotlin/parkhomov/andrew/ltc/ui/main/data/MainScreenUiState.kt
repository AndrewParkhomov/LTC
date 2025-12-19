@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.main.data

import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.LensData
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class MainScreenUiState(
    val lensInCompareList: Int = 0,
    val lensData: LensData? = null,
    val calculatedData: CalculatedData? = null,
    val calculateTransposition: Instant? = null
) {
    companion object Companion {
        fun mock(): MainScreenUiState {
            return MainScreenUiState()
        }
    }
}
