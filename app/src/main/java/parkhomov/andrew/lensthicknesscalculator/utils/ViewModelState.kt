package parkhomov.andrew.lensthicknesscalculator.utils

import androidx.annotation.StringRes
import parkhomov.andrew.base.data.result.CalculatedData

open class ViewModelState

// diameter and transposition
data class SetData(val value: Double, val viewId: Int) : ViewModelState()

data class CalculatedDiameter(val diameter: Double) : ViewModelState()

// transposition
object ClearEditText : ViewModelState()

data class CalculatedTransposition(val sphere: Double, val cylinder: Double, val axis: Double) : ViewModelState()

// thickness
data class ShowResultDialog(val data: CalculatedData?) : ViewModelState()

data class HighlightSpherePower(val isHighlight: Boolean) : ViewModelState()
data class HighlightCenterThickness(val isHighlight: Boolean) : ViewModelState()
data class HighlightDiameter(val isHighlight: Boolean) : ViewModelState()
data class BaseCurve(val baseCurve: String) : ViewModelState()

// result
data class LensCalculatedData(
        val refractionIndex: String,
        val spherePower: String,
        val cylinderPower: String?,
        val axis: String?,
        val thicknessOnAxis: Pair<String?, String?>,
        val thicknessMax: String?,
        val thicknessCenter: String,
        val thicknessEdge: String,
        val realBaseCurve: String,
        val diameter: String
) : ViewModelState()

// language
data class CheckLanguageRadioButton(val buttonId: Int) : ViewModelState()

// single activity
data class ShowSnackbar(@StringRes val id: Int) : ViewModelState()
data class CreateStringForSharing(val data: CalculatedData?) : ViewModelState()
data class MakeTabSelected(val position: Int) : ViewModelState()
data class OpenNewTab(val tabId: String) : ViewModelState()