package parkhomov.andrew.ltc.data

import androidx.compose.runtime.Immutable

@Immutable
data class CalculatedData(
    val refractionIndex: RefractiveIndexUiModel,
    val spherePower: Double,
    val cylinderPower: Double?,
    val axis: String?,
    val thicknessOnAxis: String?,
    val thicknessCenter: String,
    val thicknessEdge: String,
    val thicknessMax: String?,
    val realBaseCurve: String,
    val diameter: String
) {
    companion object Companion {
        fun mock(): CalculatedData {
            return CalculatedData(
                refractionIndex = RefractiveIndexUiModel.getDefaultIndex(),
                spherePower = -4.5,
                cylinderPower = -1.75,
                axis = "66",
                thicknessOnAxis = "8.5",
                thicknessCenter = "2",
                thicknessEdge = "7.2",
                thicknessMax = "10.3",
                realBaseCurve = "2.2",
                diameter = "70"
            )
        }
    }
}