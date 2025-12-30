@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.main.data

import androidx.compose.runtime.Immutable
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.strings.Locales
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Immutable
data class MainScreenUiState(
    val lensInCompareList: Int = 0,
    val themeId: Int? = null,
    val language: String = Locales.EN,
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
