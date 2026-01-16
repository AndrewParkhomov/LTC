@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.main.data

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndexUiModel
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
    val isLensInCompareList: Boolean = false,
    val calculateTransposition: Instant? = null,
    val showSettingsDialog: Boolean = false,
    val refractiveIndices: ImmutableList<RefractiveIndexUiModel> = persistentListOf(),
    val selectedRefractiveIndex: RefractiveIndexUiModel = RefractiveIndexUiModel.getDefaultIndex(),
    val showAddCustomIndexDialog: Boolean = false,
    val indexToDelete: RefractiveIndexUiModel? = null,
    val showIosPromoDialog: Boolean = false
) {
    companion object Companion {
        fun mock(): MainScreenUiState {
            return MainScreenUiState()
        }
    }
}
