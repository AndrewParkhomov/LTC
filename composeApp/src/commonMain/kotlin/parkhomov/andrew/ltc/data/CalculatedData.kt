package parkhomov.andrew.ltc.data

data class CalculatedData(
    val refractionIndex: String,
    val spherePower: Double,
    val cylinderPower: Double?,
    val axis: String?,
    val thicknessOnAxis: String?,
    val thicknessCenter: String,
    val thicknessEdge: String,
    val thicknessMax: String?,
    val realBaseCurve: String,
    val diameter: String,
    val isLensInCompareList: Boolean
) {
    companion object Companion {
        fun mock(): CalculatedData {
            return CalculatedData(
                refractionIndex = "1.498 CR",
                spherePower = -4.5,
                cylinderPower = -1.75,
                axis = "66",
                thicknessOnAxis = "8.5",
                thicknessCenter = "2",
                thicknessEdge = "7.2",
                thicknessMax = "10.3",
                realBaseCurve = "2.2",
                diameter = "70",
                isLensInCompareList = false
            )
        }
    }
}